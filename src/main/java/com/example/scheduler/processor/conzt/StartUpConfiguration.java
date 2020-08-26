package com.example.scheduler.processor.conzt;


public class StartUpConfiguration {

    public static class DBConfig {
        public static final String BASE_ENTITY_PACKAGE = "com.example.scheduler.processor.dt";
        public static final String HIBERNATE_INITIAL_OPERATION = "hibernate.hbm2ddl.auto";
        public static final String HIBERNATE_INITIAL_OPERATION_ACTION = "update";
        public static final String HIBERNATE_PERSISTANCE_UNIT = "external";
    }

}
