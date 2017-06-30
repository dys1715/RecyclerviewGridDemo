package winsion.recyclerviewgriddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Carriage> mList = new ArrayList<>();
    private int mColumnCount = 5; //数据列数
    private GridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        //初始化30个座位数据
        for (int i = 0; i < 33; i++) {
            mList.add(new Carriage(GridAdapter.TYPE_SEAT, ""));
        }
        //处理数据
        List<Carriage> newList = getNewList(mList);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new GridAdapter(this, newList);
        mRecyclerView.setAdapter(mAdapter);

        //-------------------------
        String seat = "5C";
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).getDetail().equals(seat)) {
                newList.get(i).setChecked(true);
            }
        }
        mAdapter.notifyDataSetChanged();
        //-------------------------
    }

    @NonNull
    private List<Carriage> getNewList(List<Carriage> list) {
        List<Carriage> newList = new ArrayList<>();
        int newSize;
        int sumLine = (int) Math.ceil(list.size() / 5f); //总行数 32/5=7行 32%5=2 最后一行2个元素
        if (list.size() % mColumnCount >= 3) {
            newSize = list.size() + sumLine * 2; //32+14=46
        } else {
            newSize = list.size() + sumLine * 2 - 1; //45
        }
        Log.e("newSize=", "" + newSize);
        for (int i = 0; i < newSize; i++) {
            if (i % 7 == 0) {
                newList.add(new Carriage(GridAdapter.TYPE_LINENUMBER, i / 7 + 1 + ""));
            } else if (i % 7 == 4) {
                newList.add(new Carriage(GridAdapter.TYPE_WAY, ""));
            } else {
                int line = i / 7 + 1;
                int column = i % 7;
                newList.add(new Carriage(GridAdapter.TYPE_SEAT, String.valueOf(line), String.valueOf(column), getSeatName(line, column), false));
            }
        }
        return newList;
    }

    /**
     * 获取座位号
     *
     * @param line
     * @param column
     * @return
     */
    private String getSeatName(int line, int column) {
        char s = (char) (column + 64); //65:A
        return String.valueOf(line) + s;
    }

    class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public static final int TYPE_LINENUMBER = 1; //行号
        public static final int TYPE_SEAT = 2; //座位
        public static final int TYPE_WAY = 3;  //过道
        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private List<Carriage> mList;

        public GridAdapter(Context context, List<Carriage> list) {
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
            mList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_LINENUMBER:
                    return new ViewHolder1(mLayoutInflater.inflate(R.layout.item_1_linenumber, parent, false));
                case TYPE_SEAT:
                    return new ViewHolder2(mLayoutInflater.inflate(R.layout.item_2_seat, parent, false));
                case TYPE_WAY:
                    return new ViewHolder3(mLayoutInflater.inflate(R.layout.item_3_way, parent, false));

            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_LINENUMBER:
                    ViewHolder1 holder1 = (ViewHolder1) holder;
                    holder1.tvLineNumber.setText(mList.get(position).getDetail());
                    break;
                case TYPE_SEAT:
                    final ViewHolder2 holder2 = (ViewHolder2) holder;
                    holder2.tvSeat.setText(mList.get(position).getDetail());
                    holder2.tvSeat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "position=" + position
                                            + ",\nline=" + mList.get(position).getLine()
                                            + ",column=" + mList.get(position).getColumn(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (mList.get(position).isChecked()) {
                        holder2.tvSeat.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder2.tvSeat.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    break;
                case TYPE_WAY:
                    ViewHolder3 holder3 = (ViewHolder3) holder;
                    break;
                default:
                    break;
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

        /**
         * 行号
         */
        class ViewHolder1 extends RecyclerView.ViewHolder {
            TextView tvLineNumber;

            public ViewHolder1(View itemView) {
                super(itemView);
                tvLineNumber = (TextView) itemView.findViewById(R.id.tv_line_number);
            }
        }

        /**
         * 座位
         */
        class ViewHolder2 extends RecyclerView.ViewHolder {
            TextView tvSeat;

            public ViewHolder2(View itemView) {
                super(itemView);
                tvSeat = (TextView) itemView.findViewById(R.id.tv_seat);
            }
        }

        /**
         * 过道
         */
        class ViewHolder3 extends RecyclerView.ViewHolder {
            TextView tvWay;

            public ViewHolder3(View itemView) {
                super(itemView);
                tvWay = (TextView) itemView.findViewById(R.id.tv_way);
            }
        }
    }
}
