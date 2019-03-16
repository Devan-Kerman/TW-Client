/**
 * 
 */
package api.modules;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * @author devan
 *
 */
public @interface Module {
	public Class<? extends Mod> getMod();
}
