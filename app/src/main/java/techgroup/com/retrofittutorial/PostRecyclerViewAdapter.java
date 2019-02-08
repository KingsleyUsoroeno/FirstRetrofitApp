package techgroup.com.retrofittutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.PostViewHolder> {

   private List<PostAttributes> postList;
   private Context mContext;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public PostRecyclerViewAdapter(Context context, RecyclerView recyclerView){
        this.mContext = context;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        if (onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_layout,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostAttributes  post = postList.get(holder.getAdapterPosition());
        holder.userId.setText(String.valueOf(post.getUserId()));
        holder.Id.setText(String.valueOf(post.getId()));
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        if (postList == null){
            return 0;
        }else
        return postList.size();
    }

    public void setPostList(List<PostAttributes> postList){
        this.postList = postList;
        notifyDataSetChanged();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView userId;
        TextView Id;
        TextView title;
        TextView body;

        public PostViewHolder(View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.TextView_UserId);
            Id = itemView.findViewById(R.id.TextView_id);
            title = itemView.findViewById(R.id.TextView_Title);
            body = itemView.findViewById(R.id.TextView_Body);
        }
    }
}
