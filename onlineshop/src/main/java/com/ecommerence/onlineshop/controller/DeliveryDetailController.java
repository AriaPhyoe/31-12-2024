package com.ecommerence.onlineshop.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.DeliveryDetail;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.service.DeliveryDetailService;
import com.ecommerence.onlineshop.service.DeliveryService;
import com.ecommerence.onlineshop.service.OrdersService;
import com.ecommerence.onlineshop.service.ShopService;

import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping("/delidetailsys")
public class DeliveryDetailController {
	@Autowired
	private OrdersService orderSer;
	
	@Autowired
	private DeliveryService deliSer;
	@Autowired
	private DeliveryDetailService delidetailSer;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private ShopService shopSer;
	
	@PostMapping("/addDelivery")
	public String addDelivery(@RequestParam("orderId") Long orderId, Model model,HttpSession httpSession) {
		
	    Orders order = orderSer.getOrderById(orderId);
	    DeliveryDetail deliveryDetail = new DeliveryDetail();
	    deliveryDetail.setOrders(order);
	    
	    model.addAttribute("deliverydetail", deliveryDetail);
	    model.addAttribute("order", order);

	    return "AddOrderDeliveryPage";
	}
	@PostMapping("/adddeliveryprocess")
	public String addDeliveryProcess(@ModelAttribute DeliveryDetail deliveryDetail, @RequestParam("deliveryemail")String deliveryemail,
			@RequestParam("delidatetime")LocalDateTime delidatetime) {
		Delivery delivery = deliSer.findDeliveryBydeliveryemail(deliveryemail);
		Long shopId = (Long) httpSession.getAttribute("shopId");
		System.out.println("shopId"+shopId);
	    shop shopadmin = shopSer.findShopByshopId(shopId);
	    System.out.println(shopadmin);
		deliveryDetail.setStatus(false);
		deliveryDetail.setDelidatetime(delidatetime);
		deliveryDetail.setShop(shopadmin);
		deliveryDetail.setDelivery(delivery);
		delidetailSer.save(deliveryDetail);
		Orders order = deliveryDetail.getOrders();
        orderSer.updateOrderStatus(order.getOrderId(), true);
	    return "redirect:/shop/showorder";
	}
	
	@GetMapping("/showdeliveryorderrequest")
    public String showdeliveryorderRequests(Model model,HttpSession httpSession) {
		String deliveryEmail =(String) httpSession.getAttribute("deliveryEmail");
		Delivery delivery = deliSer.getCustomerByEmail(deliveryEmail);
            List<DeliveryDetail> delidetail = delidetailSer.getDeliveriesOrderByDelivery(delivery);
            model.addAttribute("delidetail", delidetail);
            return "deliveryOrdersByDeli";
    }
	@PostMapping("/confirmrequest/{id}")
    public String confirmDeliveryOrderRequest(@PathVariable("id") long deliverydetailId) {
        DeliveryDetail delidetail = delidetailSer.findById(deliverydetailId);
        if (delidetail != null) {
            delidetail.setStatus(true); // Update enable status to true
            delidetailSer.save(delidetail); // Save the updated shopAdmin
        }
        return "redirect:/delidetailsys/showdeliveryorderrequest";
    }


}
