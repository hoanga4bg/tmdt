package com.tmdt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmdt.model.Electronic;
import com.tmdt.model.Supplier;
import com.tmdt.model.Types;
import com.tmdt.repository.ElectronicRepository;
import com.tmdt.repository.SupplierRepository;
import com.tmdt.repository.TypesRepository;

@Controller
@RequestMapping("/store/electronic")
public class StoreElectronicController {
	@Autowired
	private ElectronicRepository electronicRepository;
	
	@Autowired
	private TypesRepository typeRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@GetMapping
	public String electronic(Model model) {
		List<Electronic> electronics = new ArrayList<Electronic>();
		electronics = electronicRepository.findAll();
		model.addAttribute("elecs", electronics);
		model.addAttribute("amount", electronics.size());
		return "store/electronic/display";
	}
	
	@GetMapping("/create")
	public String createNewElectronic(Model model) {
		Electronic elec = new Electronic();
		List<Types> types = new ArrayList<Types>();
		types = typeRepository.findAll();
		List<Supplier> sups = new ArrayList<Supplier>();
		sups = supplierRepository.findAll();
		model.addAttribute("elec", elec);
		model.addAttribute("types", types);		
		model.addAttribute("sups", sups);
		model.addAttribute("status", 1);
		return "store/electronic/form";
	}
	
	@PostMapping
	public String saveElectronic(Electronic elec) {
		Types type = typeRepository.findOneById(elec.getType().getId());
		Supplier sup = supplierRepository.findOneById(elec.getSupplier().getId());
		elec.setType(type);
		elec.setSupplier(sup);
		electronicRepository.save(elec);
		return "redirect:/store/electronic";
	}
	
	@GetMapping("/edit")
	public String editElectronic(Model model,@RequestParam("id") String id) {
		Electronic elec = electronicRepository.findOneById(Long.parseLong(id));
		List<Types> types = new ArrayList<Types>();
		types = typeRepository.findAll();
		List<Supplier> sups = new ArrayList<Supplier>();
		sups = supplierRepository.findAll();
		model.addAttribute("elec", elec);
		model.addAttribute("types", types);		
		model.addAttribute("sups", sups);
		model.addAttribute("status", 0);
		return "store/electronic/form";
	}
	
	@GetMapping("/delete")
	public String eleteElectronic(@RequestParam("id") String id) {
		electronicRepository.deleteById(Long.parseLong(id));
		return "redirect:/store/electronic";
	}

}
