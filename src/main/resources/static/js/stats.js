document.addEventListener('DOMContentLoaded', function () {


Highcharts.chart('container', {
    chart: {
        type: 'bar'
    },
    title: {
        text: 'Traffic Flow in Porto'
    },
    xAxis: {
        categories: ['Rua de Serviço STCP', 'Rua Avenida de França', 'Estrada da Circunvalação']
    },
    yAxis: {
        title: {
            text: 'Flow'
        }
    },
    series: [{
        name: 'Number of cars',
        data: [42, 14, 18]
    }],
});



});
    
 // 
   // $("#getChart").on("click", function(){

     //   $.post( "getStatisticsData", {yyyy: 2020, mm: 05, dd: 00}, function( data ) {
           // $( ".result" ).html( data );
           // alert( "Load was performed." );
 