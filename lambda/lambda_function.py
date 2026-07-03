import json

def lambda_handler(event, context):
    print("Mensajes recibidos desde SQS:")

    for record in event["Records"]:
        body = record["body"]
        audit_event = json.loads(body)

        print("=== AUDITORIA TECHSTORE ===")
        print(f"Accion: {audit_event.get('accion')}")
        print(f"Producto ID: {audit_event.get('productoId')}")
        print(f"Nombre: {audit_event.get('nombre')}")   
        print(f"Usuario: {audit_event.get('usuario')}")
        print(f"Fecha: {audit_event.get('fecha')}")

    return {
        "statusCode": 200,
        "body": "Eventos procesados correctamente"
    }