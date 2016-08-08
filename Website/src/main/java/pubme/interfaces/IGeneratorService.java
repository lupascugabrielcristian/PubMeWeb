package pubme.interfaces;



import pubme.model.Pub;

import java.util.List;

public interface IGeneratorService {
    Pub generateRandomPub();

    List<Pub> generateRandomPubs(int number);
}
