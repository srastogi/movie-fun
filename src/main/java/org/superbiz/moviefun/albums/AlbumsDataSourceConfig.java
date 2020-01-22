package org.superbiz.moviefun.albums;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.superbiz.moviefun.DatabaseServiceCredentials;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class AlbumsDataSourceConfig {

    @Bean
    public DataSource albumsDataSource(DatabaseServiceCredentials serviceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(serviceCredentials.jdbcUrl("albums-mysql"));
        HikariConfig configuration = new HikariConfig();
        configuration.setDataSource(dataSource);
        return new HikariDataSource(configuration);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean albumsEntityManagerFactoryBean(DataSource albumsDataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(albumsDataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.getClass().getPackage().getName());
        entityManagerFactoryBean.setPersistenceUnitName("albums");
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager albumsTransactionManager(EntityManagerFactory albumsEntityManagerFactoryBean) {
        return new JpaTransactionManager(albumsEntityManagerFactoryBean);
    }
}