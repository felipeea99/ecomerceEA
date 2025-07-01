package com.ecommerce.ea.services.payments;

import com.ecommerce.ea.DTOs.request.payments.OrderItemRequest;
import com.ecommerce.ea.DTOs.request.payments.CreateOrderRequest;
import com.ecommerce.ea.DTOs.response.auth.CustomerResponse;
import com.ecommerce.ea.DTOs.response.auth.UserResponse;
import com.ecommerce.ea.DTOs.response.payments.OrderItemResponse;
import com.ecommerce.ea.DTOs.response.payments.OrderResponse;
import com.ecommerce.ea.DTOs.update.payments.OrderUpdate;
import com.ecommerce.ea.entities.auth.Customer;
import com.ecommerce.ea.entities.store.Address;
import com.ecommerce.ea.entities.store.Product;
import com.ecommerce.ea.entities.store.StatusType;
import com.ecommerce.ea.entities.auth.Store;
import com.ecommerce.ea.entities.payments.Order;
import com.ecommerce.ea.entities.payments.OrderItem;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.payments.IOrder;
import com.ecommerce.ea.repository.payments.OrderRepository;
import com.ecommerce.ea.services.auth.CustomerService;
import com.ecommerce.ea.services.auth.UserAccService;
import com.ecommerce.ea.services.store.AddressService;
import com.ecommerce.ea.services.store.ProductService;
import com.ecommerce.ea.services.auth.StoreService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrder {

    private final OrderRepository orderRepository;
    private final StoreService storeService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final AddressService addressService;
    private final  OrderItemService orderItemService;
    private final UserAccService userAccService;

    public OrderService(OrderRepository orderRepository, StoreService storeService, ProductService productService, CustomerService customerService, AddressService addressService, OrderItemService orderItemService, UserAccService userAccService) {
        this.orderRepository = orderRepository;
        this.storeService = storeService;
        this.productService = productService;
        this.customerService = customerService;
        this.addressService = addressService;
        this.orderItemService = orderItemService;
        this.userAccService = userAccService;
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        /// Search the StoreID and save it on the "store" variable & same to customerId on "customer" variable & addressId same
        Store store = storeService.findStoreByIdBaseForm(request.getStoreId());
        Customer customer = customerService.findCustomerById(request.getCustomerId());
        Address address = addressService.findAddressByIdBaseForm(request.getAddressId());

        /// Creates an Order Object
        Order order = new Order();
        order.setStatus(StatusType.PREPARING);
        order.setPaymentDate(LocalDateTime.now());
        order.setStore(store);
        order.setCustomer(customer);
        order.setAddress(address);
        /// Creates the OrderItem List
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        /// Iterates from each element from the list of "createOrderRequest.items"
        for (OrderItemRequest itemReq : request.getItems()) {
            /// Search the ProductObject by productId
            Product product = productService.findProductByIdBaseForm(itemReq.getProductId());
            /// Creates an OrderItem
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());
            /// Makes the addition in "total" variable
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            /// item gets added to the "items" list
            items.add(item);
        }
        /// Continues saving all
        order.setItems(items);
        order.setTotalAmount(total);
        /// Save it, it saves both parts do to CascadeType.All
       Order orderSaved = orderRepository.save(order);

        return ToOrderResponse(orderSaved);
    }

    /// Find the Order by OrderId and returns it as an object from the database
    @Override
    public Order findOrderByIdBaseForm(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("orderId was not found on the database"));
    }

    /// Find the Order by OrderId and returns it as a OrderResponse
    @Override
    public OrderResponse findOrderById(UUID orderId) {
        Order order = findOrderByIdBaseForm(orderId);
        return ToOrderResponse(order);
    }

    @Override
    public Order findOrderByStripePaymentId(String stripePaymentId) {
        return orderRepository.findByStripePaymentIntentId(stripePaymentId)
                .orElseThrow(() -> new BadRequestException("stripePaymentId was not found on the database"));
    }

    @Override
    public OrderResponse editOrder(OrderUpdate orderUpdate) {
        return null;
    }
    /// Deletes an order base on the orderId given
    @Override
    public Boolean deleteOrder(UUID orderId) {
        /// orderId Validation
        findOrderByIdBaseForm(orderId);
        /// Delete it
        orderRepository.deleteById(orderId);
        return true;
    }

    @Override
    public List<OrderResponse> getAllArticlesBoughtByCustomerId(UUID customerId) {
        return List.of();
    }

    @Override
    public List<OrderResponse> getAllArticlesBoughtAdmin() {
        //retrieve all the orders for Admin
        List<Order> orderList = orderRepository.findAll();
        //Transform it into shoppingHistory response type
        return orderList.stream().map(this::ToOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllArticlesBoughtByStoreId(UUID storeId) {
        return orderRepository.findAllItemsByStoreId(storeId)
                .stream().map(this::ToOrderResponse)
                .toList();
    }

    @Override
    public ByteArrayOutputStream orderExcel(UUID storeId) {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Orders");

            // Headers
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Fecha de pago");
            header.createCell(1).setCellValue("Total");
            header.createCell(2).setCellValue("Estado");
            header.createCell(3).setCellValue("Estado de pago");
            header.createCell(4).setCellValue("Id de orden");
            header.createCell(5).setCellValue("Cliente");
            header.createCell(6).setCellValue("Dirección");
            header.createCell(7).setCellValue("Productos");

            // Obtener lista de órdenes del storeId (deberías tener un repositorio para esto)
            List<Order> orders = orderRepository.findAllItemsByStoreId(storeId);

            int rowCount = 1;
            for (Order order : orders) {
                Row row = sheet.createRow(rowCount++);

                // Fecha de pago, evitar null
                String paymentDateStr = order.getPaymentDate() != null ? order.getPaymentDate().toString() : "N/A";
                row.createCell(0).setCellValue(paymentDateStr);

                // Total
                row.createCell(1).setCellValue(order.getTotalAmount() != null ? order.getTotalAmount().doubleValue() : 0.0);

                // Estado
                row.createCell(2).setCellValue(order.getStatus() != null ? order.getStatus().toString() : "N/A");

                // Estado de pago
                row.createCell(3).setCellValue(order.getPaymentStatus() != null ? order.getPaymentStatus().toString() : "N/A");

                // Id de orden
                row.createCell(4).setCellValue(order.getOrderId().toString());

                // Cliente (nombre completo)
                if (order.getCustomer() != null && order.getCustomer().getUser() != null) {
                    var user = order.getCustomer().getUser();
                    String fullName = user.getName() + " " + user.getUserLastName1() + " " + user.getUserLastName2();
                    row.createCell(5).setCellValue(fullName);
                } else {
                    row.createCell(5).setCellValue("N/A");
                }

                // Full Address
                if (order.getAddress() != null) {
                    var addr = order.getAddress();
                    String fullAddress = addr.getStreet() + " " + addr.getNumber() + " " + addr.getColony() + " " + addr.getTown() + " " + addr.getCountry();
                    row.createCell(6).setCellValue(fullAddress);
                } else {
                    row.createCell(6).setCellValue("N/A");
                }

                // Productos: concatenar nombres y cantidades de OrderItems
                if (order.getItems() != null && !order.getItems().isEmpty()) {
                    StringBuilder products = new StringBuilder();
                    for (OrderItem item : order.getItems()) {
                        String productName = item.getProduct() != null ? item.getProduct().getProductName() : "N/A";
                        int quantity = item.getQuantity();
                        products.append(productName).append(" (x").append(quantity).append("), ");
                    }
                    // eliminar última coma y espacio
                    String productStr = products.length() > 2 ? products.substring(0, products.length() - 2) : "";
                    row.createCell(7).setCellValue(productStr);
                } else {
                    row.createCell(7).setCellValue("N/A");
                }
            }

            // Auto-ajustar columnas
            for (int i = 0; i <= 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // Generar ByteArrayOutputStream para retorno
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;

        } catch (Exception e) {
            throw new RuntimeException("Error generating Excel", e);
        }
    }



    @Override
    public OrderResponse ToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        /// order.getItems is List<Order>, so here it is converted it into "orderItemResponse"
        List<OrderItemResponse> orderItemResponseList = order.getItems().stream().map(orderItemService::ToOrderItemResponse).toList();
        /// Then assign it correspondingly
        orderResponse.setItems(orderItemResponseList);
        orderResponse.setStatus(order.getStatus().toString());
        orderResponse.setPaymentDate(order.getPaymentDate());
        orderResponse.setPaymentStatus(order.getPaymentStatus().toString());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStoreName(order.getStore().getStoreName());
        /// CustomerResponse & Address validation
        UserResponse userResponse = userAccService.ToUserResponse(order.getCustomer().getUser());
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(order.getCustomer().getCustomerId());
        customerResponse.setUser(userResponse);
        if (order.getCustomer() != null)
            orderResponse.setCustomer(customerResponse);
        /// "Address" object initialization
        if (order.getAddress() != null)
          orderResponse.setAddress(addressService.ToAddressResponse(order.getAddress()));
        return orderResponse;
    }

    @Override
    public Order ToOrderObject(CreateOrderRequest createOrderRequest) {
        Store store = storeService.findStoreByIdBaseForm(createOrderRequest.getStoreId());

        Order order = new Order();
        order.setStatus(StatusType.PREPARING);
        order.setPaymentDate(LocalDateTime.now());
        order.setStore(store);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : createOrderRequest.getItems()) {
            Product product = productService.findProductByIdBaseForm(itemReq.getProductId());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            items.add(item);
        }
        order.setItems(items);
        order.setTotalAmount(total);

        return order;
    }
}

