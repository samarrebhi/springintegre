package com.ey.springboot3security.service.bloc;


import com.ey.springboot3security.entity.Bloc;
import com.ey.springboot3security.entity.Chambre;
import com.ey.springboot3security.entity.Foyer;
import com.ey.springboot3security.repository.BlocRepo;
import com.ey.springboot3security.repository.ChambreRepo;
import com.ey.springboot3security.repository.FoyerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

public class BlocServiceImpl implements IBlocService{

    BlocRepo blocRepository;
    ChambreRepo chambreRepository;
    FoyerRepo foyerRepository;

    @Override
    public Bloc addBloc(Bloc b) {
        List<Chambre> chambres = (List<Chambre>) b.getChambres();
        b = blocRepository.save(b);
        for (Chambre chambre : chambres) {
            chambre.setBloc(b);
            chambreRepository.save(chambre);
        }
        return b; //on ajoute une ligne
    }

    @Override
    public List<Bloc> addBlocs(List<Bloc> blocs) {
        return blocRepository.saveAll(blocs); //on ajoute Des lignes dans la BD
    }

    @Override
    public Bloc editBloc(Bloc b) {
        return blocRepository.save(b); //pour faire la modification
    }

    @Override
    public List<Bloc> findAll() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc findById(long id) {
        // return blocRepository.findById(id).get();
        //ou bien
        //ou bien
        return blocRepository.findById(id).orElse(Bloc.builder().idBloc(0).nomBloc("pas de bloc").build());


    }

    @Override
    public void deleteById(long id) {
        blocRepository.deleteById(id);

    }

    @Override
    public void delete(Bloc b) {
        blocRepository.delete(b);

    }

    @Override
    public List<Bloc> findByNomBloc(String nomBloc) {
        return blocRepository.findByNomBloc(nomBloc);
    }

    @Override
    public List<Bloc> findByCapaciteBloc(int capaciteBloc) {
        return blocRepository.findByCapaciteBloc(capaciteBloc);
    }

    @Override
    public List<Bloc> findByNomBlocAndCapaciteBloc(String nomBloc, int capaciteBloc) {
        return blocRepository.findByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc);
    }

    @Override
    public List<Bloc> findByNomBlocIgnoreCase(String nomBloc) {
        return blocRepository.findByNomBlocIgnoreCase(nomBloc);
    }

    @Override
    public List<Bloc> findByCapaciteBlocGreaterThan(int capacite) {
        return blocRepository.findByCapaciteBlocGreaterThan(capacite);
    }

    @Override
    public List<Bloc> findByNomBlocContaining(String subString) {
        return blocRepository.findByNomBlocContaining(subString);
    }

    @Override
    public List<Bloc> findAllByOrderByNomBlocAsc() {
        return blocRepository.findAllByOrderByNomBlocAsc();
    }

    @Override
    public List<Bloc> findByNomBlocOrCapaciteBloc(String nomBloc, int capaciteBloc) {
        return blocRepository.findByNomBlocOrCapaciteBloc(nomBloc, capaciteBloc);
    }



    @Override
    public Bloc affecterChambresABloc(List<Integer> numChambre, String nomBloc) {
        Bloc b = blocRepository.getBlocByNomBloc(nomBloc);
        numChambre.forEach(numero ->{
            Chambre c = chambreRepository.findByNumeroChambre(numero);
            c.setBloc(b);
            chambreRepository.save(c);

        });
        return b ;
    }

    @Override
    public Bloc desaffecterChambreABloc(long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);

        if (bloc != null) {
            List<Chambre> chambres = bloc.getChambres();
            for (Chambre chambre : chambres) {
                chambre.setBloc(null);
            }
            bloc.setChambres(chambres);
            blocRepository.save(bloc);
        }
        return bloc;
    }

    @Override
    public Bloc affecterBlocAFoyer(String nomBloc, String nomFoyer) {
        Bloc b = blocRepository.getBlocByNomBloc(nomBloc);
        Foyer f = foyerRepository.findFoyerByNomFoyer(nomFoyer);
        ////////////// PARENT HOWA BLOC
        b.setFoyer(f);
        return blocRepository.save(b);
    }
}
