package skyEngine.Core;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundClip
{
	private Clip clip;
	private FloatControl gain;
	private int pausedFramePosition = 0;
	private boolean isClipPaused = false;

	/**
	 * The constructor for the SoundClip class. It takes a 16-bit WAV file and
	 * decodes it so that it can be used.
	 * 
	 * @param location
	 *            The path to the WAV file
	 */
	public SoundClip(String location)
	{
		try
		{
			InputStream audioSource = getClass().getResourceAsStream(location);
			InputStream bufferedInput = new BufferedInputStream(audioSource);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufferedInput);
			AudioFormat initialFormat = audioInput.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, initialFormat.getSampleRate(),
					16, initialFormat.getChannels(), initialFormat.getChannels() * 2, initialFormat.getSampleRate(),
					false);
			AudioInputStream decodedAudioInput = AudioSystem.getAudioInputStream(decodedFormat, audioInput);

			clip = AudioSystem.getClip();
			clip.open(decodedAudioInput);

			gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * The overloaded constructor for the SoundClip class. It takes a 16-bit WAV
	 * file and decodes it so that it can be used. It also sets the default
	 * volume of the audio clip
	 * 
	 * @param location
	 *            The path to the WAV file
	 * @param volume
	 *            The volume the clip is to be played at (-80.0 - 6.0206)
	 */
	public SoundClip(String location, float volume)
	{
		try
		{
			InputStream audioSource = getClass().getResourceAsStream(location);
			InputStream bufferedInput = new BufferedInputStream(audioSource);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufferedInput);
			AudioFormat initialFormat = audioInput.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, initialFormat.getSampleRate(),
					16, initialFormat.getChannels(), initialFormat.getChannels() * 2, initialFormat.getSampleRate(),
					false);
			AudioInputStream decodedAudioInput = AudioSystem.getAudioInputStream(decodedFormat, audioInput);

			clip = AudioSystem.getClip();
			clip.open(decodedAudioInput);

			gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(volume);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Plays the clip from the beginning
	 */
	public void startClip()
	{
		if (clip == null)
		{
			return;
		}
		pausedFramePosition = 0;
		stopClip();
		clip.setFramePosition(0);
		while (!clip.isRunning())
		{
			clip.start();
		}
	}

	/**
	 * Stops the clip
	 */
	public void stopClip()
	{
		if (clip.isRunning())
		{
			clip.stop();
			pausedFramePosition = 0;
		}
	}

	/**
	 * This method works alongside unpauseClip() to stop a clip and save the
	 * frame that it was stopped at.
	 */
	public void pauseClip()
	{
		if (isClipPaused)
		{
			return;
		}
		if (clip == null)
		{
			return;
		}
		if (clip.isRunning())
		{
			isClipPaused = true;
			pausedFramePosition = clip.getFramePosition();
			stopClip();
		} else
		{
			return;
		}
	}

	/**
	 * This method works alongside pauseClip() to start a clip from the exact
	 * frame that it was paused at.
	 */
	public void unpauseClip()
	{
		if (!isClipPaused)
		{
			return;
		}
		if (clip == null)
		{
			return;
		}
		clip.setFramePosition(pausedFramePosition);
		while (!clip.isRunning())
		{
			clip.start();
		}
		isClipPaused = false;
	}

	/**
	 * Toggles whether or not the clip is paused by calling pauseClip() and
	 * unpauseClip()
	 */
	public void togglePause()
	{
		if (!isClipPaused)
		{
			pauseClip();
		} else
		{
			unpauseClip();
		}
	}

	/**
	 * Cleans up SoundClip objects
	 */
	public void closeClip()
	{
		stopClip();
		clip.drain();
		clip.close();
	}

	/**
	 * An alternative to startClip() that loops the clip indefinitely
	 */
	public void loopClip()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		while (!clip.isRunning())
		{
			clip.start();
		}
	}

	/**
	 * Adjusts the gain of a clip
	 * 
	 * @param volume
	 *            The desired volume of a clip (-80.0 - 6.0206)
	 */
	public void setVolume(float volume)
	{
		gain.setValue(volume);
	}

	public float getVolume()
	{
		return gain.getValue();
	}

	public boolean isClipPaused()
	{
		return isClipPaused;
	}
}
