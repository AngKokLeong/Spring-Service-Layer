package com.workshop.bookshelf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.workshop.bookshelf.model.Book;
import com.workshop.bookshelf.service.BookService;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }


    //Step 1: Home Page
    @GetMapping("/")
    public String home(){
        return "index";
    }

    //Step 2: Book List
    @GetMapping("/books")
   
    public String listBooks(Model model,  @RequestParam(name="genre", required = false) String genre){
        
        if (genre != null){
            List<Book> allBooksByGenre = bookService.findByGenre(genre);
            model.addAttribute("books", allBooksByGenre);
            
            return "books";
        }

        //1. Fetch data from the service layer
        List<Book> allBooks = bookService.findAll();
        

        //2. Add it to the Model under the key "books"
        //  The template accesses it with $(books)
        model.addAttribute("books", allBooks);

        // 3. Return the view name (resolves to templates/books.html)
        return "books";

    }

    //Step 3: Book Detail Page
    @GetMapping("/books/{id}") // {id} is a URL placeholder
    public String bookDetail(@PathVariable Long id, Model model, HttpServletResponse response){ //Spring extracts "2" from /books/2
        return bookService.findById(id)
                            .map(book -> {
                                model.addAttribute("book", book);
                                return "book-detail"; //resolves to templates/book-detail.html
                            })
                            .orElseGet(() -> {
                                response.setStatus(404);
                                return "error/404"; // resolves to templates/error/404.html
                            });
    }

    //Step 4a: Show the Add Book form
    @GetMapping("/books/add")
    public String showAddBookForm(Model model){
        
        // Add an empty Book to the Model so Thymeleaf can bind
        // form fields to it using th:object

        model.addAttribute("book", new Book());
        return "add-book";
    }
    
    //Step 4b: Handle the Form submit in Add Book Form
    @PostMapping("/books/add")
    public String saveBook(@Valid @ModelAttribute Book book, BindingResult result, RedirectAttributes redirectAttrs){
        
        //https://www.baeldung.com/spring-mvc-custom-validator
        if(result.hasErrors()){
            return "add-book";
        }
        
        // Persist to the in-memory list
        bookService.save(book);


        // Attach a success message so that when this method send the redirect back to the client 
        // the client is able to retrieve this success message
        redirectAttrs.addFlashAttribute("successMessage", "Book '" + book.getTitle() + "' added successfully!");

        // Redirect so the user sees the updated list
        return "redirect:/books";
    }

    @GetMapping("/books/add-many-books")
    public String showAddManyBooksForm(Model model){
       
        return "add-many-books";
    }

    @PostMapping("/books/add-many-books")
    public String showAddManyBooksForm(@ModelAttribute Book book, Model model){
       
        ArrayList<Book> bookList = new ArrayList<Book>();

        Book book1 = new Book("TEst", "ABC", "TEST THEME", 2005);
        Book book2 = new Book("FISH", "DEF", "SCREW THEME", 2000);
        Book book3 = new Book("YU", "GHI", "DEEP THEME", 2010);

        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        bookService.saveAll(bookList);

        return "redirect:/add-many-books";
    }
}
