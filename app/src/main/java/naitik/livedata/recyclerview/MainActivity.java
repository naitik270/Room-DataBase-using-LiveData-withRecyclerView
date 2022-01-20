package naitik.livedata.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import naitik.livedata.recyclerview.adapter.PostsAdapter;
import naitik.livedata.recyclerview.db.Post;
import naitik.livedata.recyclerview.helper.AddRecord;
import naitik.livedata.recyclerview.helper.Helper;
import naitik.livedata.recyclerview.listener.ButtonClickListener;
import naitik.livedata.recyclerview.listener.DialogAddButtonClickListener;
import naitik.livedata.recyclerview.listener.DialogButtonClickListener;
import naitik.livedata.recyclerview.viewModel.PostViewModel;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements ButtonClickListener {

    private PostsAdapter postsAdapter;
    private PostViewModel postViewModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvPostsLis);

        postsAdapter = new PostsAdapter(this, this);
        setAdapter();
        //get Viewmodel
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        // Create the observer which updates the UI.
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        postViewModel.getAllPosts().observe(this, posts -> postsAdapter.setData(posts));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addPost) {
            addRecordDialog(postViewModel);
            return true;
        } else if (item.getItemId() == R.id.searchPost) {
            searchRecordDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void addRecordDialog(PostViewModel postViewModel) {
        new AddRecord().showCustomDialog(getString(R.string.add_message_dialog),
                getString(R.string.add), getContext(), new DialogAddButtonClickListener() {
                    @Override
                    public void onDialogAddButtonClicked(String val1, String val2, Dialog dialog) {
                        postViewModel.savePost(new Post(val1, val2));
                        dialog.dismiss();
                    }
                });
    }

    void searchRecordDialog() {
        new Helper().showCustomDialog(getString(R.string.search_message_dialog),
                getString(R.string.search), getContext(), new DialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClicked(String query, Dialog dialog) {
                        dialog.dismiss();
                        postViewModel.searchPost(query).observe((LifecycleOwner) getContext(),
                                posts -> postsAdapter.setData(posts));
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postsAdapter);
    }

    @Override
    public void onDeleteButtonClicked(Post post) {
        postViewModel.deletePost(post);
    }

    protected Context getContext() {
        return MainActivity.this;
    }
}