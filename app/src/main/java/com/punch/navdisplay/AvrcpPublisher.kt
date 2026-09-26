package com.punch.navdisplay

import android.content.Context
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

class AvrcpPublisher(context: Context) {

    private val mediaSession = MediaSessionCompat(context, "PunchNavSession").apply {
        setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        val state = PlaybackStateCompat.Builder()
            .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f, SystemClock.elapsedRealtime())
            .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE)
            .build()
        setPlaybackState(state)
        isActive = true
    }

    fun publish(title: String, artist: String, album: String = "Tata Punch Nav") {
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
            .build()

        mediaSession.setMetadata(metadata)
    }

    fun release() {
        mediaSession.isActive = false
        mediaSession.release()
    }
}
