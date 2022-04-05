package com.bws.musclefood.itemcategory.productlist.categorytop

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.clickOnTop
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.itemcategory.productlist.SubCategory

class TopCategoryAdapter (val mList: List<SubCategory>):
    RecyclerView.Adapter<TopCategoryAdapter.ViewHolder>() {

    private var context: Context? = null

    var row_index:Int = 0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category_top_round_image, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemCategory = mList[position]
        holder.txtCategoryTop.text = itemCategory.CategoryName
       // holder.imvProduct.setImageResource(itemCategory.topImage)



       /* if(position == 0) {
            holder.itemView.setBackgroundResource(R.drawable.round_login_button)
        }*/

        val catgoryName = itemCategory.CategoryName
        if(catgoryName.equals("",true)){
            holder.itemView.visibility = View.GONE
        }



        holder.itemView.setOnClickListener() {
            Constant.pos = position
            row_index=position;
             notifyDataSetChanged();
            if (context is ProductListActivity) {
                //clickOnTop = "NO"
              //  (context as ProductListActivity).subMenuCategory()
            }
        }


        if(row_index==position){
            holder.itemView.setBackgroundResource(R.drawable.round_login_button);
        }
        else
        {
            holder.itemView.setBackgroundResource(R.drawable.round_top_sub_menu);
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){

        val txtCategoryTop: TextView = itemView.findViewById(R.id.txtCategoryTop)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)

    }
}