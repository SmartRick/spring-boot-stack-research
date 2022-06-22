package cn.smartrick.utils;

import cn.smartrick.entity.SysRedisTemp;
import cn.smartrick.mapper.SysRedisTempMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2022/6/20 10:17
 * @Author: SmartRick
 * @Description: TODO
 */
@Slf4j
@Component
public class MysqlKeyValueUtil {
    @Autowired
    private SysRedisTempMapper redisTempMapper;

    private RedisTemplate redisTemplate;

    private RedisSerializer keySerializer;
    private RedisSerializer valueSerializer;

    @Autowired
    public MysqlKeyValueUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.keySerializer = redisTemplate.getKeySerializer();
        this.valueSerializer = redisTemplate.getValueSerializer();
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            SysRedisTemp keyPre = getKeyPre(key);
            if (keyPre != null) {
                long expire = System.currentTimeMillis() + Duration.ofSeconds(time).toMillis();
                keyPre.setExpire(expire);
                return redisTempMapper.updateById(keyPre) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return getKeyPre(key) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            for (String k : key) {
                redisTempMapper.deleteById(k);
            }
        }
    }

    /**
     * 普通缓存放入, 不存在放入，存在返回
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setnx(String key, Object value) {
        try {
            if (getKeyPre(key) == null) {
                return addValuePre(key, value, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        SysRedisTemp sysRedisTemp = null;
        return (sysRedisTemp = getKeyPre(key)) == null ? null : sysRedisTemp.getRealValue();
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            SysRedisTemp keyPre = getKeyPre(key);
            if (keyPre == null) {
                return addValuePre(key, value, null);
            } else {
                keyPre.setValue(new String(valueSerializer.serialize(value)));
                return redisTempMapper.updateById(keyPre) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            SysRedisTemp keyPre = getKeyPre(key);
            if (keyPre == null) {
                return addValuePre(key, value, time);
            } else {
                keyPre.setExpire(System.currentTimeMillis() + Duration.ofSeconds(time).toMillis());
                keyPre.setValue(new String(valueSerializer.serialize(value)));
                return redisTempMapper.updateById(keyPre) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        SysRedisTemp keyPre = getKeyPre(key);
        Object realValue = keyPre.getRealValue();
        if (realValue instanceof Integer) {
            Integer integer = (Integer) realValue;
            long res = integer.longValue() + delta;
            keyPre.setValue(new String(valueSerializer.serialize(res)));
            redisTempMapper.updateById(keyPre);
            return res;
        } else if (realValue instanceof Long) {
            Long aLong = (Long) realValue;
            long res = aLong + delta;
            keyPre.setValue(new String(valueSerializer.serialize(aLong + delta)));
            redisTempMapper.updateById(keyPre);
            return res;
        }
        return -1;
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        SysRedisTemp keyPre = getKeyPre(key);
        Object realValue = keyPre.getRealValue();
        if (realValue instanceof Integer) {
            Integer integer = (Integer) realValue;
            long res = integer.longValue() - delta;
            keyPre.setValue(new String(valueSerializer.serialize(res)));
            redisTempMapper.updateById(keyPre);
            return res;
        } else if (realValue instanceof Long) {
            Long aLong = (Long) realValue;
            long res = aLong - delta;
            keyPre.setValue(new String(valueSerializer.serialize(aLong + delta)));
            redisTempMapper.updateById(keyPre);
            return res;
        }
        return -1;
    }


    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        SysRedisTemp keyPre = getKeyPre(key);
        if (keyPre != null) {
            LinkedHashMap realValue = (LinkedHashMap) keyPre.getRealValue();
            return realValue.get(item);
        }
        return null;
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            SysRedisTemp keyPre = getKeyPre(key);
            if (keyPre == null) {
                LinkedHashMap<Object, Object> objectObjectLinkedHashMap = new LinkedHashMap<>();
                objectObjectLinkedHashMap.put(item, value);
                return addValuePre(key, value,null);
            } else {
                LinkedHashMap realValue = (LinkedHashMap) keyPre.getRealValue();
                realValue.put(item, value);
                keyPre.setValue(new String(valueSerializer.serialize(realValue)));
                return redisTempMapper.updateById(keyPre) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        SysRedisTemp keyPre = getKeyPre(key);
        if (keyPre != null) {
            LinkedHashMap realValue = (LinkedHashMap) keyPre.getRealValue();
            return realValue.containsKey(item);
        }
        return false;
    }


    private boolean addValuePre(String key, Object value, Long time) {
        try {
            SysRedisTemp sysRedisTemp = new SysRedisTemp();
            sysRedisTemp.setKey(new String(keySerializer.serialize(key)));
            sysRedisTemp.setValue(new String(valueSerializer.serialize(value)));
            if (time != null) {
                sysRedisTemp.setExpire(System.currentTimeMillis() + Duration.ofSeconds(time).toMillis());
            }
            return redisTempMapper.insert(sysRedisTemp) > 0;
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("存储KEY失败{}", e.getMessage());
            return false;
        }
    }


    private SysRedisTemp getKeyPre(String key) {
        if (key == null || "".equals(key)) return null;
        String _key = new String(keySerializer.serialize(key));
        SysRedisTemp sysRedisTemp = redisTempMapper.selectById(_key);
        if (sysRedisTemp == null) return null;
        Long expire = sysRedisTemp.getExpire();
        //如果存在过期设置，查看是否已经过期
        if (expire != null && expire > 0) {
            if (System.currentTimeMillis() > expire) {
                redisTempMapper.deleteById(key);
                return null;
            }
        }
        sysRedisTemp.setRealValue(valueSerializer.deserialize(sysRedisTemp.getValue().getBytes()));
        return sysRedisTemp;
    }
}
