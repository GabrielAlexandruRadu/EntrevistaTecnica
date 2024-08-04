package com.example.core.repository;

import com.example.core.entity.PointsHistory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class PointsHistoryRepository implements PanacheRepository<PointsHistory> {
}
