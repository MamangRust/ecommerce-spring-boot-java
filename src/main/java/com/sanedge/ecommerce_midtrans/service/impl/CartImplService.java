package com.sanedge.ecommerce_midtrans.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.ecommerce_midtrans.domain.request.carts.CreateRequestCart;
import com.sanedge.ecommerce_midtrans.domain.request.carts.DeleteRequestCarts;
import com.sanedge.ecommerce_midtrans.domain.response.cart.CartResponse;
import com.sanedge.ecommerce_midtrans.exceptions.ResourceNotFoundException;
import com.sanedge.ecommerce_midtrans.mapper.CartMapper;
import com.sanedge.ecommerce_midtrans.models.Cart;
import com.sanedge.ecommerce_midtrans.models.Product;
import com.sanedge.ecommerce_midtrans.models.User;
import com.sanedge.ecommerce_midtrans.repository.CartRepository;
import com.sanedge.ecommerce_midtrans.repository.ProductRepository;
import com.sanedge.ecommerce_midtrans.repository.UserRepository;
import com.sanedge.ecommerce_midtrans.service.CartService;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;

@Service
public class CartImplService implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartImplService.class);

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartImplService(CartRepository cartRepository, ProductRepository productRepository,
            UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MessageResponse findAllUserByID(int userId) {
        try {
            long myLog = (long) userId;

            List<Cart> carts = cartRepository.findAllByUserId(myLog);
            List<CartResponse> cartResponses = CartMapper.toCartResponses(carts);

            return MessageResponse.builder()
                    .message("Carts fetched successfully")
                    .data(cartResponses)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error while getting cart by user id", e);
            return MessageResponse.builder()
                    .message("Error while getting cart by user id")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse create(CreateRequestCart request) {
        try {
            Cart cart = new Cart();

            long myProduct = (long) request.getProductId();
            long myUser = (long) request.getUserId();

            Product product = this.productRepository.findById(myProduct)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            User user = this.userRepository.findById(myUser)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            cart.setName(request.getName());
            cart.setPrice(request.getPrice());
            cart.setImage(request.getImageProduct());
            cart.setQuantity(request.getQuantity());
            cart.setWeight(request.getWeight());
            cart.setProduct(product);
            cart.setUser(user);

            Cart savedCart = cartRepository.save(cart);

            return MessageResponse.builder()
                    .message("Cart created successfully")
                    .data(CartMapper.toCartResponse(savedCart))
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            logger.error("Error while creating cart", e);
            return MessageResponse.builder()
                    .message("Error while creating cart")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse delete(int cartId) {
        try {
            long id = (long) cartId;

            Cart cart = cartRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cart not found"));

            cartRepository.delete(cart);

            return MessageResponse.builder()
                    .message("Cart deleted successfully")
                    .data(CartMapper.toCartResponse(cart))
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error while deleting cart", e);
            return MessageResponse.builder()
                    .message("Error while deleting cart")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse deleteMany(DeleteRequestCarts cartIds) {
        try {
            List<Integer> ids = cartIds.getCartIds();

            List<Long> longIds = ids.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            int deletedCount = cartRepository.deleteByIdIn(longIds);

            logger.info("Deleted {} carts", deletedCount);

            return MessageResponse.builder()
                    .message("Deleted " + deletedCount + " carts successfully")
                    .data(deletedCount)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error while deleting multiple carts", e);
            return MessageResponse.builder()
                    .message("Error while deleting multiple carts")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }

}
