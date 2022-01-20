package naitik.livedata.recyclerview.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import naitik.livedata.recyclerview.db.Post;
import naitik.livedata.recyclerview.db.PostDao;
import naitik.livedata.recyclerview.db.PostsDatabase;

public class PostViewModel extends AndroidViewModel {

    private PostDao postDao;
    private ExecutorService executorService;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postDao = PostsDatabase.getInstance(application).postDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Post>> getAllPosts() {
        return postDao.findAll();
    }

    public void savePost(Post post) {
        executorService.execute(() -> postDao.save(post));
    }

    public void deletePost(Post post) {
        executorService.execute(() -> postDao.delete(post));
    }

    public LiveData<List<Post>> searchPost(String query) {
        return postDao.findSearchValue(query);
    }
}
