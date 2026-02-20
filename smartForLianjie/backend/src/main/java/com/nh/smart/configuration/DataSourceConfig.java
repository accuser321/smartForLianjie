package com.nh.smart.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

/**
 *
 * @ClassName: PrimaryDataSourceConfig
 * @Description: springboot多数据源配置(主数据源)
 * @Author Demo
 * @DateTime 2020年3月10日 下午5:59:12
 */
@Configuration
@MapperScan(basePackages = "com.nh.smart.dao.**",sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class DataSourceConfig {

    /**
     * 分页拦截器
     */
    @Autowired
    PaginationInterceptor paginationInterceptor;

    /**
     *
     * @Title: primaryDataSource
     * @Description:  以下是多数据源的方式,用DruidDataSource替换HikariDataSource的方法
     * 				  并且在application.yml中使用spring==>datasource==>primary==>url而不是jdbc-url
     * @Author Demo
     * @DateTime 2020年3月13日 上午11:21:38
     * @return
     */
	@Primary
	@Autowired
	@Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DruidDataSource primaryDataSource() {
		DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
		return druidDataSource;
    }


	@Bean(name = "primarySqlSessionFactory")
	@Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
		MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/com/nh/**/*.xml"));

        //==========2020-03-13 对于mybatis-plus打印SQL语句重要的部分修正
        //==========		   在多数据源的使用场景下,application.yml中mybatis-plus:configuration:log-impl: org.apache.ibatis.logging.stdout.StdOutImpl是失效的
        //==========		  所以,需要通过在代码中手动注入==========
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        // 配置打印sql语句
        configuration.setLogImpl(StdOutImpl.class);
        sessionFactoryBean.setConfiguration(configuration);

        //设置分页插件
        Interceptor[] plugins = {paginationInterceptor};
        sessionFactoryBean.setPlugins(plugins);
        return sessionFactoryBean.getObject();
    }

	/**
     * 配置事务管理
     */
    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

