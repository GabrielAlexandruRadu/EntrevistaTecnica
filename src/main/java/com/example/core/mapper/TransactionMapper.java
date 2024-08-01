package com.example.core.mapper;

import com.example.core.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.example.core.entity.TransactionEntity;


@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionEntity toEntity(Transaction domain);
    Transaction toDomain(TransactionEntity entity);
}
