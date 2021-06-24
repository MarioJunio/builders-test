package br.com.builders.resource.mapper;

import java.util.List;

public interface Mapper<T, D> {

   T toEntity(final D dto);

   D toDto(final T entity);

   List<T> toListEntity(final List<D> listDto);

   List<D> toListDto(final List<T> listEntity);
}
