package kz.alishev.paginationphoto.Controller;

import kz.alishev.paginationphoto.Model.Photo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PhotoController {

    @GetMapping("/photos")
    public String getPhotos(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        File folder = new File("src/main/resources/static/photo");

        // Проверка существования папки
        if (!folder.exists() || !folder.isDirectory()) {
            model.addAttribute("error", "Folder not found or invalid.");
            return "errorPage"; // Страница ошибки
        }

        File[] files = folder.listFiles();

        // Проверка наличия файлов в папке
        if (files == null || files.length == 0) {
            model.addAttribute("error", "No photos found.");
            return "errorPage"; // Страница ошибки
        }

        // Формирование списка фотографий
        List<Photo> photos = Arrays.stream(files)
                .filter(File::isFile)
                .map(file -> new Photo(file.getName(), "/photo/" + file.getName()))
                .collect(Collectors.toList());

        int totalPhotos = photos.size();
        int totalPages = (int) Math.ceil((double) totalPhotos / size);

        // Проверка на допустимые значения страницы
        if (page < 0) page = 0;
        if (page >= totalPages) page = totalPages - 1;

        int start = page * size;
        int end = Math.min(start + size, totalPhotos);

        // Получаем фотографии для текущей страницы
        List<Photo> pagePhotos = photos.subList(start, end);

        // Добавление данных в модель
        model.addAttribute("photos", pagePhotos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // Параметры для пагинации
        model.addAttribute("nextPage", page + 1 < totalPages ? page + 1 : page);
        model.addAttribute("prevPage", page > 0 ? page - 1 : 0);

        return "photoGallery"; // Имя шаблона
    }

    // Глобальный обработчик ошибок
    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, Model model) {
        model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "errorPage"; // Страница ошибки
    }
}
