package pubme.services;



import pubme.interfaces.ILocationService;
import pubme.model.Location;
import pubme.model.Pub;

import java.util.*;

public class LocationService implements ILocationService {

    private final double minLatitude = 44.36099963;
    private final double maxLatitude = 44.5067053;
    private final double minLongitude = 25.9691453;
    private final double maxLongitude = 26.1723213;

    @Override
    public double getRandomLatitude() {
        double bound = maxLatitude - minLatitude;
        Random rand = new Random(System.currentTimeMillis());
        Double generated = rand.nextDouble() * bound;

        return generated + minLatitude;
    }

    @Override
    public double getRandomLongitude() {
        double bound = maxLongitude - minLongitude;
        Random rand = new Random(System.currentTimeMillis());
        Double generated = rand.nextDouble() * bound;

        return generated + minLongitude;
    }

    @Override
    public Location getRandomLocation() {
        return new Location(getRandomLatitude(), getRandomLongitude());
    }

    @Override
    public List<Location> getLocationsDistributed(int number) {
        List<Location> locations = new ArrayList<>();
        Random rand = new Random(System.currentTimeMillis());

        double longitudeBound = maxLongitude - minLongitude;
        double longitudeSegment = longitudeBound / number;

        double latitudeBound = maxLatitude - minLatitude;
        double latitudeSegment = latitudeBound / number;


       for (int i = 0; i < number; i++) {
           Double latitude = minLatitude + rand.nextDouble() * latitudeSegment * (i + 1);
           Double longitude = minLongitude + rand.nextDouble() * longitudeSegment * (i + 1);
           locations.add(new Location(latitude,longitude));
        }

        return locations;
    }

    public double calculateDistance(Location from, Location to) {
        final int R = 6371; // Radius of the earth
        double lat1 = from.getLatitude();
        double lat2 = to.getLatitude();
        double lon1 = from.getLongitude();
        double lon2 = to.getLongitude();
        double height = 0.0;

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    @Override
    public List<Pub> sortPubsByDistance(Location currentLocation, List<Pub> pubs) {

//        StringBuilder builder = new StringBuilder();
//        builder.append("Before: ");
//        pubs.stream().forEach(pub -> builder.append(pub.getName()).append(", "));
//        System.out.println(builder.toString());


        List<Pub> duplicate = new ArrayList<>();
        duplicate.addAll(pubs);

        Collections.sort(duplicate, new  PubsComparator(currentLocation));


//        StringBuilder builder2 = new StringBuilder();
//        builder2.append("after: ");
//        duplicate.stream().forEach(pub -> builder2.append(pub.getName()).append(", "));
//        System.out.println(builder2.toString());

        return duplicate;
    }

    class DistanceComparator implements Comparator<Location> {

        private Location currentLocation;

        public DistanceComparator(Location location) {
            this.currentLocation = location;
        }

        @Override
        public int compare(Location l1, Location l2) {
            double d1 = calculateDistance(currentLocation, l1);
            double d2 = calculateDistance(currentLocation, l2);

            if (d1 - d2 < 0.005)
                return 0;
            if (d1 < d2)
                return -1;
            else return 1;
        }
    }

    class PubsComparator implements Comparator<Pub> {

        private Location currentLocation;

        public PubsComparator(Location location) {
            this.currentLocation = location;
        }

        @Override
        public int compare(Pub p1, Pub p2) {
            double d1 = calculateDistance(currentLocation, p1.getLocation());
            double d2 = calculateDistance(currentLocation, p2.getLocation());

            if (d1 == d2)
                return 0;
            if (d1 < d2)
                return -1;
            else return 1;
        }
    }
}
