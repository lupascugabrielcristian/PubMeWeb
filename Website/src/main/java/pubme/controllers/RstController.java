package pubme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pubme.interfaces.*;
import pubme.model.*;
import pubme.requests.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class RstController {

    private ILocationService locationService;
    private IGeneratorService generator;
    private PubsRepository pubsRepository;
    private OwnersRepository ownersRepository;
    private IUploadService uploadService;
    private VisitorsRepository visitorsRepository;
    private UserRepository userRepository;

    @Autowired
    public RstController(ILocationService locationService, IGeneratorService generatorService, IUploadService uploadService, PubsRepository pubsRepository, OwnersRepository ownersRepository, VisitorsRepository visitorsRepository, UserRepository userRepository) {
        this.locationService = locationService;
        this.generator = generatorService;
        this.pubsRepository = pubsRepository;
        this.ownersRepository = ownersRepository;
        this.uploadService = uploadService;
        this.visitorsRepository = visitorsRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/database")
    public Object getDatabaseExample() {
        List<Pub> pubs = pubsRepository.findAll();

        List<Integer> totalVisitors = getVisitorsForPubs(pubs);

        return new Object(){
            public List<Pub> allpubs = pubs;
            public List<Integer> visitors = totalVisitors;
        };
    }

    @RequestMapping("/single")
    public Object getSinglePub(@RequestParam(value="id")String id) {
        Pub found = pubsRepository.findFirstById(id);
        System.out.println("Found " + found.getName());
        return found;
    }

    @RequestMapping(path = "/database/around", method = RequestMethod.POST)
    public Object getPubsAround(@RequestParam(value="latitude")double latitude, @RequestParam(value="longitude")double longitude) {
        List<Pub> allPubs = pubsRepository.findAll();

        List<Pub> sortedPubs = locationService.sortPubsByDistance(new Location(latitude, longitude), allPubs);

        return sortedPubs.subList(0, 1);
    }
    @RequestMapping(path = "/database/aroundParams", method = RequestMethod.POST)
    public Object getPubsAround(@RequestBody ClosestPubsRequest request) {
        List<Pub> allPubs =  pubsRepository.findAll();
        List<Pub> sortedPubs = locationService.sortPubsByDistance(new Location(request.getLatitude(), request.getLongitude()), allPubs);

        return sortedPubs.subList(request.getInitalIndex(), request.getBatchSize());
    }

    @RequestMapping(path = "/get/owner", method = RequestMethod.GET)
    public Owner getOwner(@RequestParam(value="id") String ownerId){
        return ownersRepository.findOneById(ownerId);
    }

    @RequestMapping(path = "/register/owner", method = RequestMethod.POST)
    public void registerOwner(@RequestBody OwnerRegisterRequest request) {
        Owner newOwner = new Owner();
        newOwner.setFullName(request.getFirstName() + " - " + request.getLastName());
        newOwner.setMail(request.getEmail());

        ownersRepository.save(newOwner);
    }

//    @RequestMapping(path = "/register/pub", method = RequestMethod.POST)
//    public Object registerPub(@RequestBody RegisterPubRequest request){
//        Pub newPub = new Pub();
//
//        newPub.setName(request.getName());
//        newPub.setType(request.getType());
//        newPub.setOwnerId(request.getOwnerId());
//        newPub.setDescription(request.getDescription());
//        newPub.setLocation(request.getLocation());
//        newPub.addImage(request.getImagePath());
//
//        Owner owner = ownersRepository.findOneById(request.getOwnerId());
//        owner.addPubId(newPub.getId());
//
//        pubsRepository.save(newPub);
//        ownersRepository.save(owner);
//
//        return new Object(){
//            public Pub registeredPub = newPub;
//            public Owner ownerOfPub = owner;
//        };
//    }
//
//    @RequestMapping(path = "/update/pub", method = RequestMethod.POST)
//    public void updatePub(@RequestBody UpdatePubRequest request) {
//
//        if(request.getPubToSave() != null){
//            System.out.println("Nu e null: " + request.getPubToSave().getName());
//            pubsRepository.save(request.getPubToSave());
//        }
//        else {
//            System.out.println("E null. NOT saved");
//        }
//    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Owner loginOwner(@RequestBody LoginRequest request){
        String email = request.getEmail();
        Owner ownerFound;

        ownerFound = ownersRepository.findOneByMail(email);

        if (ownerFound == null) {
            ownerFound = ownersRepository.findOneByFullName(email);
        }

        return ownerFound;
    }

//    @RequestMapping(path = "/ownersPubs", method = RequestMethod.GET)
//    public Object getOwnersPubs(@RequestParam(value="id")String ownerId){
//        List<Pub> pubsFound = new ArrayList<>();
//
//        Owner ownerFound = ownersRepository.findOneById(ownerId);
//        if (ownerFound == null){
//            return new Object(){
//                public String pubs = "Obviously no pub";
//                public String owner = "owner not found";
//            };
//        }
//
//
//        ownerFound.getOwnedPubsIds().forEach(pubId -> {
//            pubsFound.add(pubsRepository.findFirstById(pubId));
//        });
//
//        return new Object(){
//            public List<Pub> pubs = pubsFound;
//            public Owner owner = ownerFound;
//        };
//    }
//
//    @RequestMapping(path = "/deleteOwnerPub", method = RequestMethod.POST)
//    public @ResponseBody Object deletePubFromOwner(@RequestBody final DeletePubFromOwnerRequest request){
//        String status = "Trying to find owner";
//        boolean deleted;
//        Owner foundOwner = ownersRepository.findOneById(request.getOwnerId());
//
//        if (foundOwner != null) {
//            List <String> ownedPubs = foundOwner.getOwnedPubsIds();
//
//            if (ownedPubs == null){
//                return new Object(){
//                    public String result = "owned pubs e null";
//                    public Owner owner = foundOwner;
//                };
//            }
//            else {
//                System.out.println(ownedPubs.toString());
//            }
//
//            List<String> currentPubsIds = ownedPubs.stream().filter(pubId -> !pubId.equals(request.getPubId())).collect(Collectors.toList());
//            foundOwner.setOwnedPubsIds(currentPubsIds);
//            ownersRepository.save(foundOwner);
//            status = "Pub ownerId removed. Acum are " + currentPubsIds.size();
//            deleted = true;
//        }
//        else {
//            status = "Owner not found";
//            deleted = false;
//        }
//
//        final String finalStatus = status;
//        return new Object(){
//            public String result = finalStatus;
//            public boolean operationSuccess = deleted;
//        };
//    }
//
//    @RequestMapping("/put")
//    public void putToDatabase(@RequestParam(value="content", defaultValue = "Default")String content){
//        Pub newPub = new Pub(content);
//        newPub.setDescription("no description");
//
//        Location pubLocation = new Location(locationService.getRandomLatitude(), locationService.getRandomLongitude());
//        newPub.setLocation(pubLocation);
//
//        pubsRepository.save(newPub);
//    }
//
//    @RequestMapping("/delete")
//    public void deleteFromDatabase(@RequestParam(value="query")String query){
//        pubsRepository.deleteByName(query);
//    }
//
//
//    @RequestMapping("/generate")
//    public void generateRandomPubs(@RequestParam(value="number")int number) {
//        List<Pub> pubs = generator.generateRandomPubs(number);
//        pubsRepository.save(pubs);
//    }

    @RequestMapping(path = "/image", method = RequestMethod.POST)
    public @ResponseBody Object uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest httpRequest) {
        String status = "A apelat upload method";

        status = uploadService.upload(file, httpRequest, status);
        System.out.println(status);
        final String finalStatus = status;
        return new Object(){
            public String path = finalStatus;
        };
    }

    @RequestMapping(path = "/visitors", method = RequestMethod.GET)
    public Object getVisitorsDistribution(@RequestParam(value="pubid")String pubId) {
        VisitorsDistribution visitors = visitorsRepository.findOneByPubId(pubId);
        Pub foundPub = pubsRepository.findFirstById(pubId);
        return new Object(){
            public VisitorsDistribution distribution = visitors;
            public Pub pub = foundPub;
        };
    }

    @RequestMapping(path = "/addVisitor", method = RequestMethod.POST)
    public Object addVisitor(@RequestBody AddVisitorRequest request){

        String status = "Searching";
        VisitorsDistribution foundDistribution = visitorsRepository.findOneByPubId(request.getPubId());
        if (foundDistribution == null){
            status = "No distriburion was found";
            VisitorsDistribution newDistribution = new VisitorsDistribution();
            newDistribution.setPubId(request.getPubId());
            newDistribution.addVisitor(request.getWeekDayNumber(), request.getMonthDayNumber(), request.getUserId());
            status = "Distribution created";
            visitorsRepository.save(newDistribution);
        }
        else {
            foundDistribution.addVisitor(request.getWeekDayNumber(), request.getMonthDayNumber(), request.getUserId());
            visitorsRepository.save(foundDistribution);
            status = "Operation succeded";
        }

        final String finalStatus = status;
        return new Object(){
            public String result = finalStatus;
        };
    }


//    @RequestMapping(path = "/pub/favorite", method = RequestMethod.POST)
//    public Object addPubToFavorite (@RequestBody FavoritePubRequest request){
//        User user = userRepository.findOneById(request.getUserId());
//        boolean alreadyFavorite;
//
//        if(user == null) {
//            return new Object(){
//                public boolean operationSuccessful = false;
//                public boolean userFound = false;
//            };
//        }
//
//        List<String> favoritePubsIds = user.getFavoritePubs();
//
//        Optional<String> result = favoritePubsIds.stream().filter(id -> id.equals(request.getPubId())).findFirst();
//        if (result.isPresent()){
//            // il scot de la favorite
//            List<String> updatedFavoritePubs = favoritePubsIds.stream().filter(favoritePubId -> !favoritePubId.equals(request.getPubId())).collect(Collectors.toList());
//            user.setFavoritePubs(updatedFavoritePubs);
//            alreadyFavorite = true;
//        }
//        else {
//            user.addNewFavoritePub(request.getPubId());
//            alreadyFavorite = false;
//        }
//        userRepository.save(user);
//
//        final boolean finalAlreadyFavorite = alreadyFavorite;
//        return new Object(){
//            public boolean operationSuccessful = true;
//            public boolean wasFavorite = finalAlreadyFavorite;
//        };
//    }
//
//    @RequestMapping(path = "/get/favoritePubs", method = RequestMethod.GET)
//    public Object getFavoritePubs(@RequestParam(value = "userid")String userId) {
//        List<Pub> favoritePubs = null ;
//        String msg;
//
//
//        User user = userRepository.findOneById(userId);
//        if (user != null) {
//            List<String> favoritePubsIds = user.getFavoritePubs();
//            favoritePubs = favoritePubsIds.stream().map(pubsRepository::findFirstById).collect(Collectors.toList());
//            msg = "Found " + favoritePubs.size() + " favorite pubs";
//        }
//        else {
//            msg = "Cannot identify user with id " + userId;
//        }
//
//
//        final List<Pub> finalFavoritePubs = favoritePubs;
//        return new Object(){
//            public List<Pub> pubs = finalFavoritePubs;
//            public String message = msg;
//        };
//    }

    @RequestMapping(path = "/login/user", method = RequestMethod.POST)
    public User loginUser(@RequestBody RegisterUserRequest request){

        User loggedInUser = userRepository.findOneById(request.getId());

        if (loggedInUser == null) {
            User newUser = new User();
            newUser.setId(request.getId());
            newUser.setName(request.getName());
            userRepository.save(newUser);
            return newUser;
        }
        else {
            return loggedInUser;
        }
    }

    @RequestMapping(path = "/user/offers", method = RequestMethod.GET)
    public Object getUserOffers(@RequestParam(value = "userid")String userId){
        User user = userRepository.findOneById(userId);
        String _operation = "Not Completed";
        List<Promotion> allPromotions = new ArrayList<>();
        boolean _success = false;

        if (user == null) {
            _operation = "User not found";
        }

        List<String> favPubsIds = user.getFavoritePubs();
        if (favPubsIds != null && favPubsIds.size() > 0) {

            favPubsIds.stream().forEach(pubId -> {
                Pub pub = pubsRepository.findFirstById(pubId);
                allPromotions.addAll(pub.getPromotions());
            });
            _success = true;
            _operation = "Found " + allPromotions.size() + " promotions :)";
        }
        else {
            _operation = "There are not favorite pubs for this user";
            _success = false;
        }

        final boolean final_success = _success;
        final String final_operation = _operation;
        return new Object(){
            public boolean success = final_success;
            public String operation = final_operation;
            public List<Promotion> result = allPromotions;
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
