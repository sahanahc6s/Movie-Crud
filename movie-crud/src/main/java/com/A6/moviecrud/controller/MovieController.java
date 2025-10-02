package com.A6.moviecrud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.A6.moviecrud.entity.Movie;
import com.A6.moviecrud.repository.MovieRepository;

@Controller
public class MovieController {

	@Autowired
	MovieRepository repository;

	@GetMapping("/")
	public String loadMain() {
		return "main.html";
	}

	@GetMapping("/insert")
	public String loadInsertForm() {
		return "insert.html"; //view name
	}

	@PostMapping("/insert")
	public String saveRecord(Movie movie, ModelMap map) {
		repository.save(movie);
		map.put("message", "Movie Added Successfully");
		List<Movie> movies = repository.findAll();
	    map.put("movies", movies);
		return "fetch.html";
	}

	@GetMapping("/fetch")
	public String fetch(ModelMap map) {
		List<Movie> movies = repository.findAll();
		if (movies.isEmpty()) {
			map.put("message", "No Records Present");
			return "main.html";
		}
		map.put("movies", movies);
		return "fetch";
	}


//================= Update ==================
@GetMapping("/edit/{id}")
public String loadEditForm(@PathVariable Long id, ModelMap map) {
    Optional<Movie> opt = repository.findById(id);
    if (opt.isPresent()) {
        map.put("movie", opt.get());
        return "update.html";
    } else {
        map.put("message", "Movie not found");
        return "fetch";
    }
}

@PostMapping("/update")
public String updateRecord(Movie movie, ModelMap map) {
    repository.save(movie); // save() updates if ID exists
    map.put("message", "Movie Updated Successfully");
    List<Movie> movies = repository.findAll();
    map.put("movies", movies);
    return "fetch";
}

// ================= Delete ==================
@GetMapping("/delete/{id}")
public String deleteRecord(@PathVariable Long id, ModelMap map) {
    repository.deleteById(id);
    map.put("message", "Movie Deleted Successfully");
    
    List<Movie> movies = repository.findAll();
    map.put("movies", movies);
    return "fetch.html";
}
//@GetMapping("/delete/{id}")
//public String deleteRecord(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//    repository.deleteById(id);
//    redirectAttributes.addFlashAttribute("message", "Movie Deleted Successfully");
//    return "redirect:/fetch";
//}

}
