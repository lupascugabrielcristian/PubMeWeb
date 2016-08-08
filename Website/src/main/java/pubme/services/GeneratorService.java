package pubme.services;

import org.springframework.beans.factory.annotation.Autowired;
import pubme.interfaces.IGeneratorService;
import pubme.interfaces.ILocationService;
import pubme.interfaces.IResourceReader;
import pubme.model.Location;
import pubme.model.Pub;
import pubme.model.PubType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratorService implements IGeneratorService {
    private ILocationService locationService;
    private IResourceReader resourceReader;
    private Random rand;

    @Autowired
    public GeneratorService(ILocationService locationService, IResourceReader resourceReader) {
        this.locationService = locationService;
        this.resourceReader = resourceReader;

        rand = new Random(System.currentTimeMillis());
    }

    @Override
    public Pub generateRandomPub() {

        String name = resourceReader.getRandomName();
        Pub generatedPub = new Pub(name);
        generatedPub.setType(chooseType());
        generatedPub.setLocation(locationService.getRandomLocation());

        return generatedPub;
    }

    @Override
    public List<Pub> generateRandomPubs(int number) {
        List<Pub> pubs = new ArrayList<>();
        List<Location> locations = locationService.getLocationsDistributed(number);

        for (int i = 0; i < number; i++) {
            String name = resourceReader.getRandomName();
            Pub generatedPub = new Pub(name);
            generatedPub.setType(chooseType());
            generatedPub.setLocation(locations.get(i));
            pubs.add(generatedPub);
        }
        return pubs;
    }


    private PubType chooseType() {
        int chosen = rand.nextInt(PubType.values().length);
        return PubType.values()[chosen];
    }
}
