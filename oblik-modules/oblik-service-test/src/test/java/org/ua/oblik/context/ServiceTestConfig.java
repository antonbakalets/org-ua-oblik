package org.ua.oblik.context;

/**
 *
 * @author Anton Bakalets
 */
public class ServiceTestConfig {
    
    public final static String DAO_CONTEXT = "classpath:/org/ua/oblik/context/dao-context.xml";
    
    public final static String SERVICE_CONTEXT = "classpath:/org/ua/oblik/context/service-context.xml";
    
    public final static String JPA_TEST_CONTEXT = "classpath:/org/ua/oblik/context/jpa-test-context.xml";
    
    public final static String SERVICE_TEST_CONTEXT = "classpath:/org/ua/oblik/context/service-test-context.xml";
        
    public final static String TRANSACTION_MANAGER = "transactionManager";
    
    public final static boolean DEFAULT_ROLLBACK = false;
}
