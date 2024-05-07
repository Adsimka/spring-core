package com.adsima.spring.database.repository;

import com.adsima.spring.bpp.Audit;
import com.adsima.spring.bpp.Transaction;
import com.adsima.spring.database.entity.Company;
import com.adsima.spring.database.pool.ConnectionPool;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Audit
@Transaction
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyRepository implements CRUDRepository<Long, Company>
{
    private final ConnectionPool connectionPool;
    private final Integer poolSize;

    public CompanyRepository(@Qualifier("pool2") ConnectionPool connectionPool,
                             @Value("${db.pool.size}") Integer poolSize) {
        this.connectionPool = connectionPool;
        this.poolSize = poolSize;
    }

    @PostConstruct
    void initialize() {
        System.out.println("Initializing CompanyRepository");
    }

    @Override
    public Optional<Company> findById(Long id) {
        System.out.println("searching company by id: " + id);
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company company) {
        System.out.println("Deleting company: " + company);
    }
}
