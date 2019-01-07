package pe.ibao.agromovil.helpers.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.models.dao.ContactoDAO;
import pe.ibao.agromovil.models.dao.EmpresaDAO;
import pe.ibao.agromovil.models.dao.FundoDAO;
import pe.ibao.agromovil.models.dao.MuestrasDAO;
import pe.ibao.agromovil.models.dao.VisitaDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;
import pe.ibao.agromovil.views.ActivityVisita;

public class AdapterListVisitas extends BaseAdapter{

    private Context ctx;
    private List<VisitaVO> listVisitas;
    private ListView listView;

    public AdapterListVisitas(Context ctx, List<VisitaVO> listVisitas, ListView listView){
        this.ctx = ctx;
        this.listVisitas =listVisitas;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return listVisitas.size();
    }

    @Override
    public Object getItem(int position) {
        return listVisitas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listVisitas.get(position).getId();
    }

    private void verVisita(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ctx.startActivity(i);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ctx = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(ctx);
        v = inflater.inflate(R.layout.visita_itemeditor_list_view,null);

        final TextView tViewFechaHora = (TextView) v.findViewById(R.id.visita_tViewFechaHora);
        final TextView tViewNameEmpresa    = (TextView) v.findViewById(R.id.visita_tViewNameEmpresa);
        final TextView tViewNameFundo    = (TextView) v.findViewById(R.id.visita_tViewNameFundo);
        final TextView tViewNameCultivo  = (TextView) v.findViewById(R.id.visita_tViewNameCultivo);
        final TextView tViewNameVariedad = (TextView) v.findViewById(R.id.visita_tViewNameVariedad);
        final TextView tViewNameContacto = (TextView) v.findViewById(R.id.visita_tViewNameContacto);
        final ImageView iViewBtnView = (ImageView) v.findViewById(R.id.visita_iViewBtnView);
        final ImageView iViewBtnDelete = (ImageView) v.findViewById(R.id.visita_iViewBtnDelete);
        final VisitaVO visitaVO = listVisitas.get(position);

        tViewFechaHora.setText(visitaVO.getFechaHoraIni());
        tViewNameFundo.setText( new FundoDAO(ctx).consultarById(visitaVO.getIdFundo()).getName());
        tViewNameCultivo.setText( visitaVO.getNameCultivo());
        tViewNameVariedad.setText( visitaVO.getNameVariedad());
        if(visitaVO.getIdEmpresa()>0){
            EmpresaDAO empresaDAO  = new EmpresaDAO(ctx);
            tViewNameEmpresa.setText(empresaDAO.consultarEmpresaByid(visitaVO.getIdEmpresa()).getName());
        }
        if(!visitaVO.isStatusContactoPersonalizado()){
            if(visitaVO.getIdContacto()>0){
                ContactoDAO contactoDAO = new ContactoDAO(ctx);
                tViewNameContacto.setText(contactoDAO.consultarContactoByid(visitaVO.getIdContacto()).getName());
            }
        }else{
            tViewNameContacto.setText(visitaVO.getContactoPersonalizado());
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, ActivityVisita.class);
                i.putExtra("isEditable",false);
                i.putExtra("idVisita",visitaVO.getId());
                ctx.startActivity(i);
            }
        });


        iViewBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, ActivityVisita.class);
                i.putExtra("isEditable",true);
                i.putExtra("idVisita",visitaVO.getId());
                ctx.startActivity(i);
            }
        });


        iViewBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogClose = new Dialog(ctx);
                dialogClose.setContentView(R.layout.dialog_danger);
                TextView mensaje = (TextView)  dialogClose.findViewById(R.id.textView11);
                mensaje.setText("Esta a punto de eliminar una inspección\n¿DESEA CONTINUAR?");
                Button btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close);
                Button btnDialogAcept = (Button) dialogClose.findViewById(R.id.buton_acept);
                ImageView iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);

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



                        if(new VisitaDAO(ctx).borrarById(visitaVO.getId())){
                            listVisitas.remove(position);
                            AdapterListVisitas.super.notifyDataSetChanged();
                        }else {
                            Toast.makeText(ctx,"No se pudo Eliminar",Toast.LENGTH_LONG).show();
                        }
                        dialogClose.dismiss();
                    }
                });

                dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogClose.show();



            }
        });

        return v;
    }


}
