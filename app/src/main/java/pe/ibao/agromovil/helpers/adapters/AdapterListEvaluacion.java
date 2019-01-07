package pe.ibao.agromovil.helpers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.models.dao.EvaluacionDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.EvaluacionVO;

public class    AdapterListEvaluacion extends BaseAdapter{

    Context ctx;
    private static List<EvaluacionVO> listEvaluacion;

    public AdapterListEvaluacion(Context ctx, List<EvaluacionVO> listEvaluacion){
        this.ctx = ctx;
        this.listEvaluacion =listEvaluacion;
    }

    @Override
    public int getCount() {
        return listEvaluacion.size();
    }

    @Override
    public Object getItem(int position) {
        return listEvaluacion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listEvaluacion.get(position).getId();
    }
    /*
    private void tomarFoto(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ctx.startActivity(i);
    }*/


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View v = convertView;
        final LayoutInflater inflater = LayoutInflater.from(ctx);
        v = inflater.inflate(R.layout.evaluacion_itemeditor_list_view,null);
            TextView tViewTipoInspeccion= (TextView) v.findViewById(R.id.tipo_inspeccion);
            TextView tViewFechaHora     = (TextView) v.findViewById(R.id.fecha_hora);
            TextView tViewPorcerntaje   = (TextView) v.findViewById(R.id.porcentaje);
            ImageView btnDelete         = (ImageView)v.findViewById(R.id.deleter);




        btnDelete.setClickable(true);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tomarFoto();
             //   Toast.makeText(ctx,"Borrando"+position,Toast.LENGTH_LONG).show();
                try{
                    listEvaluacion.remove(listEvaluacion.get(position));
                    EvaluacionDAO evaluacionDAO = new EvaluacionDAO(ctx);
                    evaluacionDAO.borrarById(listEvaluacion.get(position).getId());

                }catch (Exception e){
                    Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
                    Log.d("qwerty",e.toString());
                }
            }
        });

    //    tViewTipoInspeccion.setText(listEvaluacion.get(position).getNameInspeccion());
  //      tViewFechaHora.setText(listEvaluacion.get(position).getTimeIni());
//        tViewPorcerntaje.setText(listEvaluacion.get(position).getPorcentaje());
        return v;
    }



}
