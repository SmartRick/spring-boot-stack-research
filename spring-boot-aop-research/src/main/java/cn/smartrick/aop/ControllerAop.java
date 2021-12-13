package cn.smartrick.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Date: 2021/12/13 14:39
 * @Author: SmartRick
 * @Description: SpringAop只支持切入在IOC容器中的Bean，是运行时切入实现的。通过Jdk动态代理或者cglib
 *               SpringAop和Aspectj的关系并不大，Spring的aop实现并为采用Aspectj的实现，只是语法上和切面概念上有许多共通点，属于Aspectj语法的子集
 *               SpringAop属于运行时织入，而Aspectj可以在编译时/编译后/加载时进行织入，体现在运行期间后者性能更高
 */
@Aspect
@Component
public class ControllerAop {
    /**
     * @Aspect 表明是一个切面类
     * @Component 将当前类注入到Spring容器内
     * @Pointcut 切入点，其中execution用于使用切面的连接点。使用方法：execution(方法修饰符(可选) 返回类型 方法名 参数 异常模式(可选)) ，可以使用通配符匹配字符，*可以匹配任意字符。
     * @Before 在方法前执行
     * @After 在方法后执行
     * @AfterReturning 在方法执行后返回一个结果后执行
     * @AfterThrowing 在方法执行过程中抛出异常的时候执行
     * @Around 环绕通知，就是可以在执行前后都使用，这个方法参数必须为ProceedingJoinPoint，proceed()方法就是被切面的方法，上面四个方法可以使用JoinPoint，JoinPoint包含了类名，被切面的方法名，参数等信息。
     */

    /**
     * 切入cn.smartrick.controller包下的任何方法（忽略返回值，忽略参数，包括之包）
     * 切入点表达式语法：作用域 返回类型 方法名（参数..）。
     */
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    @Pointcut("execution(public * cn.smartrick.controller.*.*(..))")
    public void controller() {
    }


    //前置通知
    @Before("controller()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("doBefore");
    }

    //后置通知
    @After("controller()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("doAfter");
    }

    //后置返回通知
    @AfterReturning("controller()")
    public void doAfterReturning(JoinPoint joinPoint) {
        System.out.println("doAfterReturning");
    }

    //后置异常通知
    @AfterThrowing("controller()")
    public void deAfterThrowing(JoinPoint joinPoint) {
        System.out.println("deAfterThrowing");
    }

    //环绕通知
    @Around("controller()")
    public Object deAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("deAround");
        return joinPoint.proceed();
    }


    /**
     * 自定义注解切面
     */
    @Pointcut("@annotation(cn.smartrick.annotation.TimeLog)")
    public void timeLogAnnotation() {
    }

    @Before("timeLogAnnotation()")
    public void customizeAnnotation(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("参数："+ Arrays.toString(args));

        System.out.println("执行时间："+simpleDateFormat.format(new Date()));
    }
}
