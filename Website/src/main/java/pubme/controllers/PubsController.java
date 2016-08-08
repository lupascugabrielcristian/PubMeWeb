package pubme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pubme.interfaces.*;
import pubme.model.*;
import pubme.requests.DeletePubFromOwnerRequest;
import pubme.requests.FavoritePubRequest;
import pubme.requests.RegisterPubRequest;
import pubme.requests.UpdatePubRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class PubsController {
    private ILocationService locationService;
    private IGeneratorService generator;
    private PubsRepository pubsRepository;
    private OwnersRepository ownersRepository;
    private IUploadService uploadService;
    private VisitorsRepository visitorsRepository;
    private UserRepository userRepository;

    @Autowired
    public PubsController(ILocationService locationService, IGeneratorService generatorService, IUploadService uploadService, PubsRepository pubsRepository, OwnersRepository ownersRepository, VisitorsRepository visitorsRepository, UserRepository userRepository) {
        this.locationService = locationService;
        this.generator = generatorService;
        this.pubsRepository = pubsRepository;
        this.ownersRepository = ownersRepository;
        this.uploadService = uploadService;
        this.visitorsRepository = visitorsRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "/ownersPubs", method = RequestMethod.GET)
    public Object getOwnersPubs(@RequestParam(value="id")String ownerId){
        List<Pub> pubsFound = new ArrayList<>();

        Owner ownerFound = ownersRepository.findOneById(ownerId);
        if (ownerFound == null){
            return new Object(){
                public String pubs = "Obviously no pub";
                public String owner = "owner not found";
            };
        }


        ownerFound.getOwnedPubsIds().forEach(pubId -> {
            pubsFound.add(pubsRepository.findFirstById(pubId));
        });

        return new Object(){
            public List<Pub> pubs = pubsFound;
            public Owner owner = ownerFound;
        };
    }

    @RequestMapping(path = "/deleteOwnerPub", method = RequestMethod.POST)
    public @ResponseBody Object deletePubFromOwner(@RequestBody final DeletePubFromOwnerRequest request){
        String status = "Trying to find owner";
        boolean deleted;
        Owner foundOwner = ownersRepository.findOneById(request.getOwnerId());

        if (foundOwner != null) {
            List <String> ownedPubs = foundOwner.getOwnedPubsIds();

            if (ownedPubs == null){
                return new Object(){
                    public String result = "owned pubs e null";
                    public Owner owner = foundOwner;
                };
            }
            else {
                System.out.println(ownedPubs.toString());
            }

            List<String> currentPubsIds = ownedPubs.stream().filter(pubId -> !pubId.equals(request.getPubId())).collect(Collectors.toList());
            foundOwner.setOwnedPubsIds(currentPubsIds);
            ownersRepository.save(foundOwner);
            status = "Pub ownerId removed. Acum are " + currentPubsIds.size();
            deleted = true;
        }
        else {
            status = "Owner not found";
            deleted = false;
        }

        final String finalStatus = status;
        return new Object(){
            public String result = finalStatus;
            public boolean operationSuccess = deleted;
        };
    }

    @RequestMapping("/put")
    public void putToDatabase(@RequestParam(value="content", defaultValue = "Default")String content){
        Pub newPub = new Pub(content);
        newPub.setDescription("no description");

        Location pubLocation = new Location(locationService.getRandomLatitude(), locationService.getRandomLongitude());
        newPub.setLocation(pubLocation);

        pubsRepository.save(newPub);
    }

    @RequestMapping("/delete")
    public void deleteFromDatabase(@RequestParam(value="query")String query){
        pubsRepository.deleteByName(query);
    }


    @RequestMapping("/generate")
    public void generateRandomPubs(@RequestParam(value="number")int number) {
        List<Pub> pubs = generator.generateRandomPubs(number);
        pubsRepository.save(pubs);
    }

    @RequestMapping(path = "/register/pub", method = RequestMethod.POST)
    public Object registerPub(@RequestBody RegisterPubRequest request){
        Pub newPub = new Pub();

        newPub.setName(request.getName());
        newPub.setType(request.getType());
        newPub.setOwnerId(request.getOwnerId());
        newPub.setDescription(request.getDescription());
        newPub.setLocation(request.getLocation());
        newPub.addImage(request.getImagePath());

        Owner owner = ownersRepository.findOneById(request.getOwnerId());
        owner.addPubId(newPub.getId());

        pubsRepository.save(newPub);
        ownersRepository.save(owner);

        return new Object(){
            public Pub registeredPub = newPub;
            public Owner ownerOfPub = owner;
        };
    }

    @RequestMapping(path = "/update/pub", method = RequestMethod.POST)
    public void updatePub(@RequestBody UpdatePubRequest request) {

        if(request.getPubToSave() != null){
            System.out.println("Nu e null: " + request.getPubToSave().getName());
            pubsRepository.save(request.getPubToSave());
        }
        else {
            System.out.println("E null. NOT saved");
        }
    }

    @RequestMapping(path = "/pub/favorite", method = RequestMethod.POST)
    public Object addPubToFavorite (@RequestBody FavoritePubRequest request){
        User user = userRepository.findOneById(request.getUserId());
        boolean alreadyFavorite;

        if(user == null) {
            return new Object(){
                public boolean operationSuccessful = false;
                public boolean userFound = false;
            };
        }

        List<String> favoritePubsIds = user.getFavoritePubs();

        Optional<String> result = favoritePubsIds.stream().filter(id -> id.equals(request.getPubId())).findFirst();
        if (result.isPresent()){
            // il scot de la favorite
            List<String> updatedFavoritePubs = favoritePubsIds.stream().filter(favoritePubId -> !favoritePubId.equals(request.getPubId())).collect(Collectors.toList());
            user.setFavoritePubs(updatedFavoritePubs);
            alreadyFavorite = true;
        }
        else {
            user.addNewFavoritePub(request.getPubId());
            alreadyFavorite = false;
        }
        userRepository.save(user);

        final boolean finalAlreadyFavorite = alreadyFavorite;
        return new Object(){
            public boolean operationSuccessful = true;
            public boolean wasFavorite = finalAlreadyFavorite;
        };
    }

    @RequestMapping(path = "/get/favoritePubs", method = RequestMethod.GET)
    public Object getFavoritePubs(@RequestParam(value = "userid")String userId) {
        List<Pub> favoritePubs = null ;
        String msg;
        List<Integer> totalVisitors = new ArrayList<>();


        User user = userRepository.findOneById(userId);
        if (user != null) {
            List<String> favoritePubsIds = user.getFavoritePubs();
            favoritePubs = favoritePubsIds.stream().map(pubsRepository::findFirstById).collect(Collectors.toList());
            totalVisitors = getVisitorsForPubs(favoritePubs);

            msg = "Found " + favoritePubs.size() + " favorite pubs";
        }
        else {
            msg = "Cannot identify user with id " + userId;
        }


        final List<Pub> finalFavoritePubs = favoritePubs;
        final List<Integer> finalTotalVisitors = totalVisitors;
        return new Object(){
            public List<Pub> pubs = finalFavoritePubs;
            public List<Integer> visitors = finalTotalVisitors;
            public String message = msg;
        };
    }

    private List<Integer> getVisitorsForPubs(List<Pub> pubs) {
        Function<Pub, Integer> getVisitors = pub -> {
            VisitorsDistribution distribution = visitorsRepository.findOneByPubId(pub.getId());
            if (distribution == null){
                return 0;
            }
            else return distribution.getTotalVisitors();
        };

        List<Integer> totalVisitors = pubs.stream().map(getVisitors).collect(Collectors.toList());
        return totalVisitors;
    }
}
