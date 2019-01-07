package pe.ibao.agromovil.helpers.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.models.dao.MuestrasDAO;
import pe.ibao.agromovil.models.dao.RecomendacionDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.RecomendacionVO;

public class AdapterListRecomendacion extends BaseAdapter{

    private Context ctx;
    private ListView listView;
    private List<RecomendacionVO> recomendacionList;
    private boolean isEditable;

    public AdapterListRecomendacion(Context ctx, List<RecomendacionVO> recomendacionList, Boolean isEditable) {
        this.ctx = ctx;
        this.isEditable = isEditable;
        this.recomendacionList = recomendacionList;
    }

    @Override
    public int getCount() {
        return recomendacionList.size();
    }

    @Override
    public Object getItem(int position) {
        return recomendacionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recomendacionList.get(position).getId();
    }

    public boolean isAllValid(){
        for(RecomendacionVO vo : recomendacionList){
            if(vo.getCantidad().isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ctx = parent.getContext();
        View v = convertView;
         LayoutInflater inflater = LayoutInflater.from(ctx);
        v = inflater.inflate(R.layout.recomendacion_itemeditor_listview,null);
         TextView tViewTipoInspeccion= (TextView) v.findViewById(R.id.tViewNameCriterioRecomendacion);
        final EditText eTextCantidad = (EditText) v.findViewById(R.id.rec_eTextCantidad);
        final EditText eTextComentario = (EditText) v.findViewById(R.id.reco);
        Spinner spnUnidades = (Spinner) v.findViewById(R.id.rec_spnUnidades);
        Spinner spnFreciencia = (Spinner) v.findViewById(R.id.rec_spnFrecuencia);
        ImageView btnDelete = (ImageView) v.findViewById(R.id.rec_iViewBtnDelete);


        eTextComentario.setFocusable(true);
        eTextComentario.setClickable(true);


        if(!isEditable){
            btnDelete.setVisibility(View.INVISIBLE);
            spnUnidades.setEnabled(false);
            spnFreciencia.setEnabled(false);
            eTextCantidad.setFocusable(false);
            eTextCantidad.setClickable(false);
            eTextComentario.setFocusable(false);
            eTextComentario.setClickable(false);
        }



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    final Dialog dialogClose = new Dialog(ctx);
                    dialogClose.setContentView(R.layout.dialog_danger);
                    Button btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close);
                    Button btnDialogAcept = (Button) dialogClose.findViewById(R.id.buton_acept);
                    ImageView iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);
                    TextView mensaje = (TextView) dialogClose.findViewById(R.id.textView11);
                    mensaje.setText("Esta a punto de eliminar una recomendación\n¿DESEA CONTINUAR?");
                    iViewDialogClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClose.dismiss();
                        }
                    });
                    btnDialogClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogClose.dismiss();

                        }
                    });

                    btnDialogAcept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean x = new RecomendacionDAO(ctx).borrarById(recomendacionList.get(position).getId());
                            if(!x)
                            {
                                Toast.makeText(ctx, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }else

                            {
                               // Toast.makeText(ctx, "Eliminado", Toast.LENGTH_SHORT).show();
                            }
                            recomendacionList.remove(position);
                            AdapterListRecomendacion.super.notifyDataSetChanged();
                            dialogClose.dismiss();
                        }
                    });

                    dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogClose.show();

                }catch (Exception e){
                    Log.d("delete",e.toString());
                }


            }
        });
        /****

         spn unidades
         */

        String tempList = recomendacionList.get(position).getListUnidades();
        String[] parts = tempList.split("-");
        List<String> list = new ArrayList<String>();

        for(int i=0;i<parts.length;i++){
            list.add(parts[i]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ctx,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUnidades.setAdapter(dataAdapter);

        spnUnidades.setSelection(Integer.valueOf(recomendacionList.get(position).getUnidad()));

        spnUnidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                recomendacionList.get(position).setUnidad(i);
                /**falta cambiar en la base de  datos
                 */
                new RecomendacionDAO(ctx).editarUnidadById(recomendacionList.get(position).getId(),i);

            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        /****

         spn frecuencia
         */

        String temp2List = recomendacionList.get(position).getListFrecuancias();
        String[] parts2 = temp2List.split("-");
        List<String> list2 = new ArrayList<String>();

        for(int i=0;i<parts2.length;i++){
            list2.add(parts2[i]);
        }

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(ctx,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFreciencia.setAdapter(dataAdapter2);

        spnFreciencia.setSelection(Integer.valueOf(recomendacionList.get(position).getFrecuencia()));

        spnFreciencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                recomendacionList.get(position).setFrecuencia(i);
                /**falta cambiar en la base de  datos
                 */
                new RecomendacionDAO(ctx).editarFrecuenciaById(recomendacionList.get(position).getId(),i);

            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });



        /***
        cantidad
        */
        eTextCantidad.setText(recomendacionList.get(position).getCantidad());
        //     nameitem.setText(listMuestas.get(position).getName()+" "+listMuestas.get(position).getMagnitud());
        eTextCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recomendacionList.get(position).setCantidad(eTextCantidad.getText().toString());
                new RecomendacionDAO(ctx).editarCantidadById(recomendacionList.get(position).getId(),recomendacionList.get(position).getCantidad());
                //AdapterListMuestras.super.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        eTextComentario.setText(recomendacionList.get(position).getComentario());
        //     nameitem.setText(listMuestas.get(position).getName()+" "+listMuestas.get(position).getMagnitud());
        eTextComentario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recomendacionList.get(position).setComentario(eTextComentario.getText().toString());
                new RecomendacionDAO(ctx).editarComentarioById(recomendacionList.get(position).getId(),recomendacionList.get(position).getComentario());
                //AdapterListMuestras.super.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tViewTipoInspeccion.setText(recomendacionList.get(position).getName());
       return v;
    }
}
