package com.yapp.ndgl.feature.travel.traveldetail.component

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.ui.util.dropShadow
import com.yapp.ndgl.feature.travel.traveldetail.TravelPlace
import com.yapp.ndgl.feature.travel.traveldetail.getColor

@Composable
internal fun TravelMap(
    places: List<TravelPlace>,
    onScrollEnabledChange: (Boolean) -> Unit,
) {
    val routePoints = remember(places) {
        places.map { place ->
            LatLng(place.latitude, place.longitude)
        }
    }

    val dashedPattern = remember {
        listOf(
            Dash(20f),
            Gap(30f),
        )
    }

    val initialPosition = CameraPosition.fromLatLngZoom(
        LatLng(places.first().latitude, places.first().longitude),
        15f,
    )

    val cameraPositionState = rememberCameraPositionState {
        position = initialPosition
    }

    LaunchedEffect(places) {
        val cameraUpdate = when {
            places.size == 1 -> {
                CameraUpdateFactory.newLatLngZoom(LatLng(places.first().latitude, places.first().longitude), 15f)
            }

            else -> {
                val bounds = LatLngBounds.Builder().apply {
                    places.forEach { include(LatLng(it.latitude, it.longitude)) }
                }.build()
                CameraUpdateFactory.newLatLngBounds(bounds, 100)
            }
        }

        cameraUpdate.let { cameraPositionState.animate(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .motionEventSpy {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> onScrollEnabledChange(false)
                        MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> onScrollEnabledChange(true)
                    }
                },
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                mapToolbarEnabled = false,
                zoomControlsEnabled = false,
                scrollGesturesEnabled = true,
                zoomGesturesEnabled = true,
                tiltGesturesEnabled = false,
                rotationGesturesEnabled = false,
                scrollGesturesEnabledDuringRotateOrZoom = false,
                compassEnabled = false,
                myLocationButtonEnabled = false,
            ),
        ) {
            Polyline(
                points = routePoints,
                color = NDGLTheme.colors.black400,
                width = 10f,
                pattern = dashedPattern,
                startCap = RoundCap(),
                endCap = RoundCap(),
            )

            places.forEach { place ->
                PlaceMarker(place = place)
            }
        }
    }
}

@Composable
private fun PlaceMarker(
    place: TravelPlace,
) {
    val markerState = remember(place.id) { MarkerState(position = LatLng(place.latitude, place.longitude)) }

    MarkerComposable(
        state = markerState,
        anchor = Offset(0.5f, 0.5f),
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .dropShadow(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.3f),
                    offsetY = 1.dp,
                    blur = 2.dp,
                )
                .background(color = place.placeType.getColor(), shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                place.sequence.toString(),
                color = NDGLTheme.colors.white,
                style = NDGLTheme.typography.bodyMdSemiBold,
            )
        }
    }
}
