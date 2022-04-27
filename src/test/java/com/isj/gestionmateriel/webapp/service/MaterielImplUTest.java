package com.isj.gestionmateriel.webapp.service;

import com.isj.gestionmateriel.webapp.exception.AfrinnovBusinessException;
import com.isj.gestionmateriel.webapp.model.dto.MaterielDTO;
import com.isj.gestionmateriel.webapp.model.entities.Materiel;
import com.isj.gestionmateriel.webapp.repository.MaterielRepository;
import com.isj.gestionmateriel.webapp.service.mapper.MaterielMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MaterielImplUTest {

    @InjectMocks
    MaterielImpl materielImpl;

    @Mock
    private MaterielMapper materielMapper;

    @Mock
    private MaterielRepository materielRepository;

    @Test
    public void should_save_materiel() throws AfrinnovBusinessException {
        //Given
        MaterielDTO materielDTO = MaterielDTO.builder()
                .reference("ref123")
                .nom("laptop")
                .dateAcquisition("12/02/2021")
                .etat("Neuf")
                .description("ordinateur de bureau")
                .build();

        when(materielRepository.save(materielMapper.toEntity(materielDTO)))
                .thenReturn(Materiel.builder()
                        .id(Long.valueOf(1))
                        .build());
        //When
        int idMateriel = materielImpl.ajouterMateriel(materielDTO);

        //Then
        assertThat(idMateriel).isNotNull();
        assertEquals(idMateriel,1);
    }

    @Test
    public void should_failled_when_materiel_not_found() throws AfrinnovBusinessException {
        String reference="ref12536";
        catchThrowable(()->materielImpl.detailMateriel(reference));
        verify(materielRepository).findMaterielByReference(reference);
    }


}
