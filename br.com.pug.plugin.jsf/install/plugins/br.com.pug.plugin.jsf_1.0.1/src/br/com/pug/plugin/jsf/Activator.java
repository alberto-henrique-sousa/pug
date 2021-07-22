/*******************************************************************************
 * Copyright 2016 Pug Plugin. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.pug.plugin.jsf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wb.internal.core.BundleResourceProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Lists;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class Activator extends AbstractUIPlugin {
	
  public static final String PLUGIN_ID = "br.com.pug.plugin.jsf";
  private static Activator m_plugin;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Life cycle
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public void stop(BundleContext context) throws Exception {
    m_plugin = null;
    super.stop(context);
  }

  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    m_plugin = this;
  }

  /**
   * @return the shared instance.
   */
  public static Activator getDefault() {
    return m_plugin;
  }

  /**
   * @return this {@link Bundle}, can be used even without starting this plugin.
   */
  private static Bundle getBundleStatic() {
    return Platform.getBundle(PLUGIN_ID);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Files
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * @return array of entry sub-paths in given path.
   */
  public static String[] getEntriesPaths(String path) {
    List<String> entryPaths;
    {
      entryPaths = Lists.newArrayList();
      Enumeration<String> entryPathsEnumeration = getBundleStatic().getEntryPaths(path);
      CollectionUtils.addAll(entryPaths, entryPathsEnumeration);
    }
    // remove ".svn" files (for case when we use runtime workbench)
    for (Iterator<String> I = entryPaths.iterator(); I.hasNext();) {
      String entryPath = I.next();
      if (entryPath.indexOf(".svn") != -1) {
        I.remove();
      }
    }
    // convert to array
    return entryPaths.toArray(new String[entryPaths.size()]);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Resources
  //
  ////////////////////////////////////////////////////////////////////////////
  private static final BundleResourceProvider m_resourceProvider =
      BundleResourceProvider.get(PLUGIN_ID);

  /**
   * @return the {@link InputStream} for file from plugin directory.
   */
  public static InputStream getFile(String path) {
    return m_resourceProvider.getFile(path);
  }

  /**
   * @return the {@link Image} from "icons" directory, with caching.
   */
  public static Image getImage(String path) {
    return m_resourceProvider.getImage("icons/" + path);
  }

  /**
   * @return the {@link ImageDescriptor} from "icons" directory.
   */
  public static ImageDescriptor getImageDescriptor(String path) {
    return m_resourceProvider.getImageDescriptor("icons/" + path);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Preferences
  //
  ////////////////////////////////////////////////////////////////////////////
  public static IPreferenceStore getStore() {
    return m_plugin.getPreferenceStore();
  }

	/**
	 * Reads and returns the content of a text file embedded in the plugin jar
	 * file.
	 * @param filepath the file path to the text file
	 * @return null if the file could not be read
	 */
	public static String readEmbeddedTextFile(String filepath) {
		Bundle bundle = null;
		synchronized (Activator.class) {
			if (m_plugin != null) {
				bundle = m_plugin.getBundle();
			} else {
				return null;
			}
		}

		// attempt to get a file to one of the template.
		try {
			URL url = bundle.getEntry("/" + filepath);
			if (url != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(url.openStream()));

				String line;
				StringBuilder total = new StringBuilder(reader.readLine());
				while ((line = reader.readLine()) != null) {
					total.append('\n');
					total.append(line);
				}

				return total.toString();
			}
		} catch (MalformedURLException e) {
			// we'll just return null.
		} catch (IOException e) {
			// we'll just return null.
		}

		return null;
	}	
  
}
