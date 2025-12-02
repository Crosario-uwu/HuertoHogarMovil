package com.example.huertohogarmovil.ui.screens.admin

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.viewmodel.ProductoViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScanProductoScreen(
    navController: NavController,
    productoVM: ProductoViewModel
) {
    val context = LocalContext.current

    var codigoDetectado by remember { mutableStateOf<String?>(null) }
    var tienePermiso by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // PEDIR PERMISO
    LaunchedEffect(Unit) {
        if (!tienePermiso) {
            ActivityCompat.requestPermissions(
                (context as android.app.Activity),
                arrayOf(Manifest.permission.CAMERA),
                101
            )
        }
    }

    // Si no hay permiso
    if (!tienePermiso) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("Se requiere permiso de cámara")
        }
        return
    }

    // Si QR ya fue detectado
    codigoDetectado?.let { code ->
        LaunchedEffect(code) {
            productoVM.cargarProductos()
            navController.navigate(AppScreen.ProductoDetalle.createRoute(code)) {
                popUpTo(AppScreen.AdminScan.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escanear Producto") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Text(
                "Apunte la cámara al código del producto",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(20.dp))

            CameraPreview { resultado ->
                codigoDetectado = resultado
            }
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreview(onDetect: (String) -> Unit) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp),
        factory = { ctx ->
            val previewView = PreviewView(ctx)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({

                val cameraProvider = cameraProviderFuture.get()

                // PREVIEW
                val preview = androidx.camera.core.Preview.Builder()
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                // ANALIZADOR MLKIT
                @Suppress("DEPRECATION") val analyzer = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { analysis ->

                        analysis.setAnalyzer(
                            ContextCompat.getMainExecutor(ctx)
                        ) { imageProxy ->

                            procesarImagenMLKit(imageProxy, onDetect)
                        }
                    }

                // BACK CAMERA
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        analyzer
                    )

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}

@ExperimentalGetImage
private fun procesarImagenMLKit(
    imageProxy: ImageProxy,
    onDetect: (String) -> Unit
) {
    val mediaImage = imageProxy.image ?: run {
        imageProxy.close()
        return
    }

    val image = InputImage.fromMediaImage(
        mediaImage,
        imageProxy.imageInfo.rotationDegrees
    )

    val scanner = BarcodeScanning.getClient()

    scanner.process(image)
        .addOnSuccessListener { barcodes ->
            for (barcode in barcodes) {
                val rawValue = barcode.rawValue
                if (rawValue != null) {
                    onDetect(rawValue)
                    break
                }
            }
        }
        .addOnCompleteListener {
            imageProxy.close()
        }
}


