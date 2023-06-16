package acorn.calendar.config.control;

import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Aspect
@Configuration
@RequiredArgsConstructor
@ConditionalOnExpression("${database.transaction.enabled:true}")
public class TransactionConfig {

//    @Value("${database.transaction.timeout-read:10}")
//    private int readTimeout;
//
//    @Value("${database.transaction.timeout-write:10}")
//    private int writeTimeout;
//
//    @Value("${database.transaction.pattern:execution(* acorn.calendar..*Service.*(..))}")
//    private String aopPointcutExpression;
//
//    private final PlatformTransactionManager transactionManager;
//
//    @Bean
//    public TransactionInterceptor txAdvice() {
//
//        TransactionInterceptor txAdvice = new TransactionInterceptor();
//        Properties txAttributes = new Properties();
//
//        //읽기
//        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
//        readOnlyAttribute.setReadOnly(true);
//        readOnlyAttribute.setTimeout(readTimeout);
//
//        //쓰기
//        List<RollbackRuleAttribute> rollbackRules = new ArrayList<RollbackRuleAttribute>();
//        rollbackRules.add(new RollbackRuleAttribute(Exception.class));
//        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
//        writeAttribute.setTimeout(writeTimeout);
//
//        String readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString();
//        String writeTransactionAttributesDefinition = writeAttribute.toString();
//
//        //읽기 쓰기 패턴
//        txAttributes.setProperty("select*", readOnlyTransactionAttributesDefinition);
//        txAttributes.setProperty("delete*", writeTransactionAttributesDefinition);
//        txAttributes.setProperty("update*", writeTransactionAttributesDefinition);
//        txAttributes.setProperty("insert*", writeTransactionAttributesDefinition);
//        txAttributes.setProperty("merge*", writeTransactionAttributesDefinition);
//
//        txAdvice.setTransactionAttributes(txAttributes);
//        txAdvice.setTransactionManager(transactionManager);
//
//        return txAdvice;
//    }
//
//    @Bean
//    public Advisor txAdviceAdvisor() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(aopPointcutExpression);
//        return new DefaultPointcutAdvisor(pointcut, txAdvice());
//    }
}
