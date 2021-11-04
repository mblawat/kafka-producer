package com.grapeup.training.kafkaproducer.dtos;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class DTOModelMapper extends ModelMapper {

    public <S, T> Optional<T> mapOptional(Optional<S> source, Class<T> destinationType) {
        if (source.isPresent()) {
            return Optional.of(map(source.get(), destinationType));
        } else {
            return Optional.empty();
        }
    }

    public <S, T> Iterable<T> mapIterable(Iterable<S> source, Class<T> targetClass) {
        return StreamSupport.stream(source.spliterator(), false).map(element -> map(element, targetClass)).toList();
    }

}
