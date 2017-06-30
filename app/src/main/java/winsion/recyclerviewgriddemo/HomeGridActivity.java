package winsion.recyclerviewgriddemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dys on 2017/6/29 0029.
 */
public class HomeGridActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<HomeGrid> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        for (int i = 0; i < 10; i++) {
            if (i < 2) {
                mList.add(new HomeGrid(HomeGridAdapter.TYPE_WHOLE, R.mipmap.ic_launcher, null, -1));
            } else if (i == 2) {
                mList.add(new HomeGrid(HomeGridAdapter.TYPE_LONG, R.mipmap.ic_launcher, null, getResources().getColor(R.color.colorPrimary)));
            } else if (i == 7) {
                mList.add(new HomeGrid(HomeGridAdapter.TYPE_NORMAL_IMG, R.mipmap.ic_launcher, "Y", getResources().getColor(R.color.colorPrimaryDark)));
            } else {
                mList.add(new HomeGrid(HomeGridAdapter.TYPE_NORMAL, R.mipmap.ic_launcher_round, i + "", getResources().getColor(R.color.colorAccent)));
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(new HomeGridAdapter(this, mList));
    }

    /**
     * multiple adapter
     */
    class HomeGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private List<HomeGrid> mList;
        private LayoutInflater mLayoutInflater;
        public static final int TYPE_LONG = 1;
        public static final int TYPE_NORMAL = 2;
        public static final int TYPE_NORMAL_IMG = 3;
        public static final int TYPE_WHOLE = 4;

        public HomeGridAdapter(Context context, List<HomeGrid> list) {
            mContext = context;
            mList = list;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_NORMAL) {
                return new ViewHolderNormal(mLayoutInflater.inflate(R.layout.item_home_normal, parent, false));
            } else {
                return new ViewHolderLong(mLayoutInflater.inflate(R.layout.item_home_img, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == TYPE_NORMAL) {
                ViewHolderNormal holderNormal = (ViewHolderNormal) holder;
                holderNormal.ivIcon.setImageResource(mList.get(position).getIconPicId());
                holderNormal.tvName.setText(mList.get(position).getIconName());
                holderNormal.llRoot.setBackgroundColor(mList.get(position).getIconBgColor());
            } else {
                ViewHolderLong holderLong = (ViewHolderLong) holder;
                holderLong.mImageView.setImageResource(mList.get(position).getIconPicId());
                if (mList.get(position).getIconBgColor() != -1){
                    holderLong.mImageView.setBackgroundColor(mList.get(position).getIconBgColor());
                }
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mList.get(position).getType();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type = getItemViewType(position);
                        if (type == TYPE_WHOLE) {
                            return gridLayoutManager.getSpanCount();
                        }
                        if (type == TYPE_LONG) {
                            return 4;
                        }
                        if (type == TYPE_NORMAL) {
                            return 2;
                        }
                        if (type == TYPE_NORMAL_IMG) {
                            return 2;
                        }
                        return 2;
                    }
                });
            }
        }

        /**
         * long
         */
        private class ViewHolderLong extends RecyclerView.ViewHolder {
            ImageView mImageView;

            public ViewHolderLong(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.iv_img);
            }
        }

        /**
         * normal
         */
        private class ViewHolderNormal extends RecyclerView.ViewHolder {
            LinearLayout llRoot;
            TextView tvName;
            ImageView ivIcon;

            public ViewHolderNormal(View itemView) {
                super(itemView);
                llRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            }
        }
    }
}
