package com.alkotzer.itzik.anydotest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alkotzer.itzik.anydotest.R;
import com.alkotzer.itzik.anydotest.interfaces.OnItemClickListener;
import com.alkotzer.itzik.anydotest.model.BasketItem;

import java.util.ArrayList;

/**
 * Created by itzikalkotzer on 30/04/2018.
 */

public class BasketItemsListAdapter extends RecyclerView.Adapter<BasketItemsListAdapter.BasketItemViewHolder>
{
    private static final String LOG_TAG = BasketItemsListAdapter.class.getSimpleName();
    private final ArrayList<BasketItem> mItem = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;

    public BasketItemsListAdapter(final Context context)
    {
        mContext = context;
    }
public void setOnItemClickListener(OnItemClickListener listener)
{

    mListener = listener;
}

    @NonNull
    @Override
    public BasketItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType)
    {
        View itemLayoutView = LayoutInflater.from(mContext).inflate(R.layout.item_basket, parent, false);
        return new BasketItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BasketItemViewHolder holder, final int position)
    {
        BasketItem basketItem = mItem.get(position);
        int color = 0;
        try
        {
            color = Color.parseColor(basketItem.getBagColor());
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG, "onBindViewHolder(), Failed to create color:"+ basketItem.getBagColor(),e);
        }
        holder.mBagColor.getBackground().setColorFilter(color, PorterDuff.Mode.ADD);
        holder.mItemName.setText(basketItem.getName());
        holder.mItemWeight.setText(basketItem.getWeight());
        final int bagColor = color;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                if(mListener != null)
                {
                    mListener.onItemClick(holder.mBagColor,bagColor);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mItem.size();
    }

    public void addItem(final BasketItem item)
    {
        mItem.add(0, item);
        notifyDataSetChanged();
    }

    class BasketItemViewHolder extends RecyclerView.ViewHolder
    {

        private final View mBagColor;
        private final TextView mItemName;
        private final TextView mItemWeight;

        public BasketItemViewHolder(final View itemView)
        {
            super(itemView);
            mBagColor = itemView.findViewById(R.id.item_color);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemWeight = itemView.findViewById(R.id.item_weight);

        }
    }
}
