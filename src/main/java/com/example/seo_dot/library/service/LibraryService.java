package com.example.seo_dot.library.service;

import com.example.seo_dot.book.domain.Book;
import com.example.seo_dot.book.repository.BookRepository;
import com.example.seo_dot.library.domain.ColorCode;
import com.example.seo_dot.library.domain.Library;
import com.example.seo_dot.library.model.RequestLibraryDto;
import com.example.seo_dot.library.model.ResponseLibraryDto;
import com.example.seo_dot.library.model.ResponseLibraryBookListDto;
import com.example.seo_dot.library.model.ResponseDataDto;
import com.example.seo_dot.library.repository.LibraryRepository;
import com.example.seo_dot.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    public ResponseDataDto createLibrary(RequestLibraryDto requestLibraryDto, UserDetailsImpl userDetailsImpl) {
        Long userId = userDetailsImpl.getUser().getId();
        Library library = Library.builder()
                .category(requestLibraryDto.getCategory())
                .colorCode(ColorCode.random())
                .userId(userId)
                .build();

        libraryRepository.save(library);
        return ResponseDataDto.ok();
    }

    public ResponseDataDto updateLibrary(Long id, RequestLibraryDto requestLibraryDto) {
        libraryRepository.findById(id).orElseThrow().updateLibrary(requestLibraryDto);
        return ResponseDataDto.ok();
    }

    public ResponseDataDto deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
        return ResponseDataDto.ok();
    }

    @Transactional(readOnly = true)
    public List<ResponseLibraryDto> readLibraryList(UserDetailsImpl userDetailsImpl) {
        return libraryRepository.findAllByUserId(userDetailsImpl.getUser().getId()).stream()
                .map(ResponseLibraryDto::new)
                .collect(Collectors.toList());
    }

    public ResponseDataDto addBookmark(Long bookId, Long libraryId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        Library library = libraryRepository.findById(libraryId).orElseThrow();
        book.addBookmark(library);
        library.createThumbnail(book);
        return ResponseDataDto.ok();
    }

    public ResponseDataDto cancelBookmark(Long bookId) {
        bookRepository.findById(bookId).orElseThrow().cancelBookmark();
        return ResponseDataDto.ok();
    }

    public List<ResponseLibraryBookListDto> getBookmarkList(Long bookmarkId) {
        return bookRepository.findAllByBookmarkId(bookmarkId).stream()
                .map(ResponseLibraryBookListDto::new)
                .collect(Collectors.toList());
    }
}