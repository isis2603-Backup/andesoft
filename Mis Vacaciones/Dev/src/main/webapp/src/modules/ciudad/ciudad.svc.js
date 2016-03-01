/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function(ng)
{
var mod = ng.module("AppCiudad");
mod.service('ciudadSVC', [function ()
   {
       var itinerario = [];
       var logueado=false;

       this.darLog= function()
        {
          // console.log("hola"+logueado);
           return this.logueado;
       };

       this.cambiarLog=function(){
           this.logueado=true;
          console.log("hola"+this.logueado);
       };

       this.logout=function(){
           this.logueado=false;
          console.log("hola"+this.logueado);
       };

       this.agregarCiudad = function(ciudad)
       {
           console.log("entra a metodo agregar ciudad Servicio");

           itinerario.push(ciudad);
       };

       this.borrarCity = function(ciudad)
       {
             var indice = itinerario.indexOf(ciudad);
            itinerario.splice(indice,1);
       };

       this.darItinerario= function()
        {
           return itinerario;
       };

   }
    ]);
})(window.angular);
