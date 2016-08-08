package pubme.interfaces;

import pubme.model.Location;
import pubme.model.Pub;

import java.util.List;

public interface ILocationService {
    double getRandomLongitude();
    double getRandomLatitude();

    Location getRandomLocation();

    List<Location> getLocationsDistributed(int number);

    List<Pub> sortPubsByDistance(Location currentLocation, List<Pub> pubs);
}
