package com.example.omarchh.minegociotest.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Activitys.HistorialCierresCaja;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mCierre;
import com.example.omarchh.minegociotest.Model.mDetalleMovCaja;
import com.example.omarchh.minegociotest.Model.mResumenFlujoCaja;
import com.example.omarchh.minegociotest.Model.mResumenMedioPago;
import com.example.omarchh.minegociotest.Model.mResumenTotalVentas;
import com.example.omarchh.minegociotest.Model.mVentasPorHora;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterResumenCaja;
import com.example.omarchh.minegociotest.RvAdapterResumenMedioPago;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;

public class resumenFlujoCaja extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {

    final int CODE_RESULT_ID_CAJA = 1;
    List<mResumenFlujoCaja> listA;
    List<mResumenFlujoCaja> listB;
    ScrollView scrollView, sv;
    RelativeLayout rlProgress, rl;
    mCierre cierre;
    int idCierreCaja;
    String texto = "";
    String texto2 = "";
    String fechaInicio, fechaFinal;
    RvAdapterResumenCaja rvAdapterResumenCaja, rvAdapterResumenCajaB;
    CrearVista crearVista;
    RecyclerView rvA, rvB, rvResumenMedioPago;
    ImageView imgBarchart;
    TextView titulo1;
    TextView titulo2;
    TextView txtEstadoCaja, txtPeriodoCaja, txtNumVentas, txtValorTotalDato, txtPromedioVentas;
    CardView cvSelectCierre;
    RvAdapterResumenMedioPago rvAdapterResumenMedioPago;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
    private BarChart mChart;

    public resumenFlujoCaja() {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void setCrearVista(CrearVista crearVista) {

        this.crearVista = crearVista;

    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resumen_flujo_caja, container, false);

        titulo1 = (TextView) v.findViewById(R.id.txtTitulo1);
        titulo2 = (TextView) v.findViewById(R.id.txtTitulo2);
        txtEstadoCaja = (TextView) v.findViewById(R.id.txtEstadoCierre);
        txtPeriodoCaja = (TextView) v.findViewById(R.id.txtPeriodoCaja);
        cvSelectCierre = (CardView) v.findViewById(R.id.cv_select_cierre);
        rlProgress = (RelativeLayout) v.findViewById(R.id.rlProgressBar);
        scrollView = (ScrollView) v.findViewById(R.id.svDatos);
        imgBarchart = (ImageView) v.findViewById(R.id.imgBarChart);

        cvSelectCierre.setOnClickListener(this);

        rvAdapterResumenCaja = new RvAdapterResumenCaja();
        rvAdapterResumenCajaB = new RvAdapterResumenCaja();
        mChart = (BarChart) v.findViewById(R.id.barChartResumen);
        rvA = (RecyclerView) v.findViewById(R.id.rvResumenA);
        rvB = (RecyclerView) v.findViewById(R.id.rvResumenB);
        txtNumVentas = (TextView) v.findViewById(R.id.txtNumVentas);
        txtValorTotalDato = (TextView) v.findViewById(R.id.txtValorTotalDato);
        txtPromedioVentas = (TextView) v.findViewById(R.id.txtPromedioVentas);
        rvResumenMedioPago = (RecyclerView) v.findViewById(R.id.rvResumenMedioPago);
        rvResumenMedioPago.setLayoutManager(new LinearLayoutManager(getContext()));

        rvAdapterResumenMedioPago = new RvAdapterResumenMedioPago();
        rvResumenMedioPago.setAdapter(rvAdapterResumenMedioPago);
        rvResumenMedioPago.setHasFixedSize(true);
        rvResumenMedioPago.setNestedScrollingEnabled(false);
        rvA.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvA.setNestedScrollingEnabled(false);
        rvA.setAdapter(rvAdapterResumenCaja);
        rvA.setHasFixedSize(true);

        rvB.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvB.setNestedScrollingEnabled(false);
        rvB.setAdapter(rvAdapterResumenCajaB);
        rvB.setHasFixedSize(true);
        mChart.setVisibility(View.GONE);
        imgBarchart.setVisibility(View.VISIBLE);

        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
        mChart.getLegend().setEnabled(false);
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);

        mChart.setDrawBorders(false);
        txtNumVentas.setText("0");
        txtValorTotalDato.setText("0");

        return v;
    }

    public void ProcesoCabeceraResumen(mResumenTotalVentas resumen) {

        // new ObtenerResumenCierre().execute(0);
        if (resumen.getNum_Ventas() <= 0) {
            txtNumVentas.setText(String.valueOf(0));
        } else {
            txtNumVentas.setText(String.valueOf(resumen.getNum_Ventas()));
        }
        if (resumen.getMonto_Total() != null) {
            txtValorTotalDato.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", resumen.getMonto_Total()));
        } else {
            txtValorTotalDato.setText(Constantes.DivisaPorDefecto.SimboloDivisa + "0.00");
        }
        if (resumen.getMonto_PromedioVentas() != null) {

            txtPromedioVentas.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", resumen.getMonto_PromedioVentas()));
        } else {
            txtPromedioVentas.setText(Constantes.DivisaPorDefecto.SimboloDivisa + "0.00");
        }

    }

    public void ObtenerDatosVentas(final List<mVentasPorHora> list) {
        int count;
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> yVals = new ArrayList<>();
        final ArrayList<String> xVals = new ArrayList<>();

        if (list != null) {
            count = list.size();
            if (list.size() == 0) {
                imgBarchart.setVisibility(View.VISIBLE);
                mChart.setVisibility(View.GONE);
            } else if (list.size() > 0) {
                mChart.setVisibility(View.VISIBLE);
                for (int i = 0; i < count; i++) {

                    yVals.add(new BarEntry(i, list.get(i).getMontoPorHora().intValueExact()));
                    xVals.add(new String(String.valueOf(list.get(i).getHoraInicio())) + "-" + new String(String.valueOf(list.get(i).getHoraInicio() + 1)) + "h   ");


                }

                BarDataSet barDataSet = new BarDataSet(yVals, "");
                BarData data = new BarData(barDataSet);
                ;
                barDataSet.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        return " ";
                    }
                });

                barDataSet.setColor(Color.parseColor("#00BDFF"));
                mChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", value);
                    }
                });
                mChart.getDescription().setText("Valor de ventas por hora");
                mChart.getAxisRight().setEnabled(false);
                mChart.animateY(1000);
                final XAxis xAxis = mChart.getXAxis();
                xAxis.setEnabled(true);
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(new IAxisValueFormatter() {


                                            @Override
                                            public String getFormattedValue(float value, AxisBase axis) {
                                                if (xVals.size() > (int) value) {
                                                    return xVals.get((int) value);
                                                } else {

                                                    return " ";
                                                }


                                            }

                                        }

                );
                mChart.setData(data);


            }
        } else {
            imgBarchart.setVisibility(View.VISIBLE);
            mChart.setVisibility(View.GONE);
        }


    }

    public void MostrarPantalla() {
        rlProgress.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    public void OcultarPantalla() {

        rlProgress.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);


    }

    public void CambiarInfoResumenIngresoRetiro(mDetalleMovCaja mov, int idMedioPago) {

        if (mov.getTipoRegistro() == 1) {
            listB.get(2).setMonto(listB.get(2).getMonto().add(mov.getMonto()));
            rvAdapterResumenCajaB.ChangeElement(listB.get(2), (byte) 1);
            rvAdapterResumenMedioPago.ChangeElement(mov, idMedioPago, (byte) 1);

        } else if (mov.getTipoRegistro() == 2) {
            listB.get(3).setMonto(listB.get(3).getMonto().add(mov.getMonto()));
            rvAdapterResumenCajaB.ChangeElement(listB.get(3), (byte) 2);
            rvAdapterResumenMedioPago.ChangeElement(mov, idMedioPago, (byte) 2);
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.cv_select_cierre:
                Intent intent = new Intent(getContext(), HistorialCierresCaja.class);
                startActivityForResult(intent, CODE_RESULT_ID_CAJA);
                break;

        }
    }

    public void ModificarDatosCabecera(mCierre cierre) {

        fechaInicio = dateFormat.format(cierre.getFechaApertura());
        if (cierre.getEstadoCierre().equals("A")) {
            txtEstadoCaja.setText("Periodo");
            fechaFinal = " hasta ahora";

        } else if (cierre.getEstadoCierre().equals("C")) {
            txtEstadoCaja.setText("Caja cerrada");
            if (cierre.getFechaCierre() != null) {
                fechaFinal = " a " + dateFormat.format(cierre.getFechaCierre());
            } else {
                fechaFinal = "-";
            }
        }
        txtPeriodoCaja.setText(fechaInicio + fechaFinal);
    }

    public void ModificarResumenPorMedioPago(List<mResumenMedioPago> mResumenMedioPagoList) {
        rvAdapterResumenMedioPago.AddElement(mResumenMedioPagoList);
        rvAdapterResumenMedioPago.notifyDataSetChanged();

    }

    public void ModificarResumenFlujoCaja(List<mResumenFlujoCaja> list, List<mResumenMedioPago> mResumenMedioPagoList) {

        listA = new ArrayList<>();
        listB = new ArrayList<>();
        if (list != null) {
            for (mResumenFlujoCaja resumenFlujoCaja : list) {
                if (resumenFlujoCaja.getCodtitulo().equals("1")) {
                    listA.add(resumenFlujoCaja);
                } else if (resumenFlujoCaja.getCodtitulo().equals("2")) {
                    listB.add(resumenFlujoCaja);
                }

            }
            texto = listA.get(0).getTitutloPago();
            texto2 = listB.get(0).getTitutloPago();
            rvAdapterResumenCaja.AddElement(listA);
            rvAdapterResumenCajaB.AddElement(listB);

            titulo1.setText(texto);
            titulo2.setText(texto2);
            rvAdapterResumenCaja.notifyDataSetChanged();
            rvAdapterResumenCajaB.notifyDataSetChanged();
            rvAdapterResumenMedioPago.AddElement(mResumenMedioPagoList);
            rvAdapterResumenMedioPago.notifyDataSetChanged();

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_RESULT_ID_CAJA) {
            if (resultCode == RESULT_CANCELED) {

            } else {
                idCierreCaja = data.getIntExtra("idCierre", 0);

                crearVista.ObtenerIdCierre(idCierreCaja);
                //           new ObtenerResumenCierre().execute(idCierreCaja);
            }

        }
    }

    public interface CrearVista {

        public void VistaCreada();

        public void ObtenerIdCierre(int idCierre);
    }
}
