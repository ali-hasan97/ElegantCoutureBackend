package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.Listing;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ListingService {
    List<Listing> getAllListing();
    Listing getListingByID(int productID);

    Listing addListing(Listing listing);

    Listing updateListing(Listing listing);

    String deleteListingByID(int productID);
}
