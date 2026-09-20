export interface LogResponse {
    idLog: number;
    usuario: string;
    modulo: string;
    accion: string;
    operacion: string;
    descripcion: string;
    exitoso: boolean;
    fechaLog: string;
}