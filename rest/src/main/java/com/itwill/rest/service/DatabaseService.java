package com.itwill.rest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
	
	 	@Value("${spring.datasource.url}")
	    private String datasourceUrl;
	    /**
	     * 현재 사용 중인 데이터베이스 타입을 반환.
	     * @return 데이터베이스 타입 ("oracle", "mysql", "postgresql", "sqlserver" 중 하나)
	     */
	    public String getDatabaseType() {
	        if (datasourceUrl.contains("oracle")) {
	            return "oracle";
	        } else if (datasourceUrl.contains("mysql")) {
	            return "mysql";
	        } else {
	            throw new IllegalArgumentException("Unsupported database type in URL: " + datasourceUrl);
	        }
	    }
}
