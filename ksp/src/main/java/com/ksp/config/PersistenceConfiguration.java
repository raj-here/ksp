package com.ksp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ksp.repository.impl.ExtendedRepositoryImpl;

/**
 * this class will be user for database connection and related operations .
 * 
 * @author AKS
 * @since V1.0.0_03082019
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
		"com.ksp.repository" }, repositoryBaseClass = ExtendedRepositoryImpl.class)
public class PersistenceConfiguration {

}
