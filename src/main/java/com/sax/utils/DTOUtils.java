package com.sax.utils;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DTOUtils{
    @Getter
    private static final DTOUtils instance = new DTOUtils();
    private final ModelMapper modelMapper;

    private DTOUtils() {
        this.modelMapper = new ModelMapper();
    }

    public <S, D> D converter(S source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <S, D> List<D> convertToDTOList(List<S> sourceList, Class<D> destinationType) {
        return sourceList.stream()
                .map(source -> modelMapper.map(source, destinationType))
                .collect(Collectors.toList());
    }
    public <S, D> Set<D> convertToDTOSet(Set<S> sourceList, Class<D> destinationType) {
        return sourceList.stream()
                .map(source -> modelMapper.map(source, destinationType))
                .collect(Collectors.toSet());
    }
}
