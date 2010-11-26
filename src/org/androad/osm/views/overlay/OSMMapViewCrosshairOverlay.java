// Created by plusminus on 17:10:58 - 17.12.2008
package org.andnav2.osm.views.overlay;

import org.andnav.osm.util.GeoPoint;
import org.andnav.osm.views.OpenStreetMapView;
import org.andnav.osm.views.OpenStreetMapView.OpenStreetMapViewProjection;
import org.andnav.osm.views.overlay.OpenStreetMapViewOverlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

public class OSMMapViewCrosshairOverlay extends OpenStreetMapViewOverlay {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Paint mPaint;
	private final int mCenterCircleRadius;

	// ===========================================================
	// Constructors
	// ===========================================================

    public OSMMapViewCrosshairOverlay(final Context ctx){
		this(ctx, Color.BLACK, 1, 0);
	}

	public OSMMapViewCrosshairOverlay(final Context ctx, final int aCrosshairColor, final int aCrosshairWidth, final int aCenterCircleRadius) {
		this(ctx, aCrosshairColor, aCrosshairWidth, aCenterCircleRadius, Style.STROKE, true);
	}

	public OSMMapViewCrosshairOverlay(final Context ctx, final int aCrosshairColor, final int aCrosshairWidth, final int aCenterCircleRadius, final Style aCenterCircleStyle, final boolean aAntialiasing) {
        super(ctx);

		this.mPaint = new Paint();
		this.mPaint.setStyle(aCenterCircleStyle);
		this.mPaint.setAntiAlias(aAntialiasing);
		this.mPaint.setColor(aCrosshairColor);
		this.mPaint.setStrokeWidth(aCrosshairWidth);
		this.mCenterCircleRadius = aCenterCircleRadius;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setCrosshairColor(final int aColor){
		this.mPaint.setColor(aColor);
	}

	public void setCrosshairWidth(final int aWidth){
		this.mPaint.setStrokeWidth(aWidth);
	}

	// ===========================================================
	// Methods from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onDraw(final Canvas canvas, final OpenStreetMapView osmv) {
        final OpenStreetMapViewProjection pj = osmv.getProjection();
        final GeoPoint mapcenter = osmv.getMapCenter();
        final Point mappointcenter = pj.toMapPixels(mapcenter, null);

		final int height = mappointcenter.y * 2;
		final int width = mappointcenter.x * 2;

		final int height_2 = mappointcenter.y;
		final int width_2 = mappointcenter.x;

		canvas.drawCircle(width_2, height_2, this.mCenterCircleRadius, this.mPaint);

		/* Draw line from left to the centercircle. */
		canvas.drawLine(0, height_2, width_2 - this.mCenterCircleRadius, height_2, this.mPaint);

		/* Draw line from right to the centercircle. */
		canvas.drawLine(width_2 + this.mCenterCircleRadius, height_2, width, height_2, this.mPaint);

		/* Draw line from top to the centercircle. */
		canvas.drawLine(width_2, 0, width_2, height_2 - this.mCenterCircleRadius, this.mPaint);

		/* Draw line from bottom to the centercircle. */
		canvas.drawLine(width_2, height_2 + this.mCenterCircleRadius, width_2, height, this.mPaint);
	}

	@Override
	protected void onDrawFinished(final Canvas c, final OpenStreetMapView osmv) {
		// Nothing to draw.
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}