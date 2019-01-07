package pe.ibao.agromovil.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.models.dao.UsuarioDAO;
import pe.ibao.agromovil.models.dao.VisitaDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentMain extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ConstraintLayout layoutVertical= null;
    ConstraintLayout layoutHorizontal= null;

    RelativeLayout layoutNewInspection = null;
    RelativeLayout layoutNewInspection2 = null;

    private OnFragmentInteractionListener mListener;

    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



/*
        Toast.makeText(this.getContext(),getRotation(this.getContext()),
                Toast.LENGTH_SHORT).show();
*/


        /*
        if(getRotation(this.getContext()).equals("vertical")){ //es vertical o portrait.

        }else{ // es horizontal o landscape.

        }
*/
    }


    public String getRotation(Context context){
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                return "vertical";
            case Surface.ROTATION_90:
            default:
                return "horizontal";
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        if( new VisitaDAO(getContext()).getEditing() != null){
            TextView tv1 = (TextView) getView().findViewById(R.id.tViewInspeccionStatus1);
            TextView tv2 = (TextView) getView().findViewById(R.id.tViewInspeccionStatus2);
                tv1.setText("Continuar Inspección");
                tv2.setText("Continuar Inspección");
        }

        String NombreU = new UsuarioDAO(getContext()).verficarLogueo().getName();

        ((TextView)getView().findViewById(R.id.fmain_tViewSaludo1)).setText(NombreU);
        ((TextView)getView().findViewById(R.id.fmain_tViewSaludo2)).setText(NombreU);

        layoutNewInspection = (RelativeLayout) getView().findViewById(R.id.layout_new_Inspection);
        layoutNewInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openNewInspection();

            }
        });

        layoutNewInspection2 = (RelativeLayout) getView().findViewById(R.id.layout_new_Inspection2);
        layoutNewInspection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewInspection();

            }
        });

        layoutVertical =  (ConstraintLayout) getView().findViewById(R.id.lay_vertical);
        layoutHorizontal =  (ConstraintLayout) getView().findViewById(R.id.lay_horizontal);
        if(getRotation(this.getContext()).equals("vertical")){ //es vertical o portrait.
            layoutHorizontal.setVisibility(View.INVISIBLE);
            layoutVertical.setVisibility(View.VISIBLE);
        }else{ // es horizontal o landscape.
            layoutVertical.setVisibility(View.INVISIBLE);
            layoutHorizontal.setVisibility(View.VISIBLE);
        }

    }

    void openNewInspection(){

        Intent i;
        i = new Intent(getActivity(), ActivityVisita.class);


        String lat="";
        String lon="";
        if(new VisitaDAO(getContext()).getEditing()==null){
            lat = String.valueOf(ActivityMain.getCurrentLatitude());
            lon =String.valueOf(ActivityMain.getCurrentLongitude());
            VisitaVO visitaTemp = new VisitaDAO(getContext()).intentarNuevo();
            new VisitaDAO(getContext()).setLatLonIniById(visitaTemp.getId(),lat,lon);
            visitaTemp = new VisitaDAO(getContext()).intentarNuevo();
            i.putExtra("isEditable", true);
            i.putExtra("idVisita", visitaTemp.getId());
        }else {
            VisitaVO visitaTemp = new VisitaDAO(getContext()).intentarNuevo();
            i.putExtra("isEditable", true);
            i.putExtra("idVisita", visitaTemp.getId());
        }
        i.putExtra("isClosedVisita",false);
        startActivity(i);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: ActivityUpdate argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
