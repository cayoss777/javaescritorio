# AGENTS.md

## Project overview

Single-module Maven Java 21 desktop Swing app (MySQL-backed login system).
No framework, no DI, no tests, no CI, no linters.

Entrypoint: `com.mycompany.uailogin.UaiLogin#main` (`src/main/java/com/mycompany/uailogin/UaiLogin.java`)

## Build & run

```bash
mvn compile          # compile only (no plugins configured beyond default)
mvn package          # produces target/uaiLogin-1.0-SNAPSHOT.jar
mvn clean            # removes target/
```

No Maven wrapper — `mvn` must be on PATH.

## Architecture

| Layer | Package | Key file |
|---|---|---|
| View (Swing JFrames) | `VistaVentas` | `frmLogin.java`, `frmVentas.java`, `frmPrincipal.java` |
| Controller | `ControlVendedor` | `Control_Vendedor.java` |
| Model | `ModeloVendedor` | `Vendedor.java` |
| DB connection | `ConexionBD` | `ConexionMySql.java` |
| bd_dos Config | `bd_dos.config` | `DatabaseConfig.java` |
| bd_dos Model | `bd_dos.modelo` | `Cliente.java`, `Producto.java`, `Factura.java`, etc. |
| bd_dos DAO | `bd_dos.dao` | `ClienteDAO.java`, `FacturaDAO.java`, etc. |
| bd_dos Controller | `bd_dos.control` | `ClienteController.java`, `FacturaController.java` |
| bd_dos View | `bd_dos.vista` | `MainFrame.java`, `FacturaPanel.java` |

Entrypoint for bd_dos module: `bd_dos.vista.MainFrame#main`

## Gotchas

- **`.gitignore` now exists** — covers `target/`, `db.properties`, `*.log`. The real `db.properties` must be copied from `src/main/resources/db.properties.example` and filled with correct credentials. The example file is tracked; the real one is ignored.
- **NetBeans GUI forms**: `*.form` files and `//GEN-BEGIN:initComponents` / `//GEN-END:initComponents` blocks are auto-generated. Do not edit — the Form Editor regenerates them.
- **Database credentials are hardcoded** in `ConexionBD/ConexionMySql.java:17-21` (user `root`, empty password, `jdbc:mysql://localhost:3306/bd_uai_login`). Expects table `tabla_vendedor` with columns `idVendedor`, `nombreVendedor`, `password`.
- **bd_dos uses proper config** — `DatabaseConfig.java` loads from `db.properties` (classpath) or env vars (`BD_DOS_URL`, `BD_DOS_USER`, `BD_DOS_PASSWORD`). Copy `db.properties.example` → `db.properties`.
- **Package naming is inconsistent** — some are under `com.mycompany.uailogin`, others are flat (`ConexionBD`, `ControlVendedor`, `ModeloVendedor`, `VistaVentas`). Match existing style when adding files. The `bd_dos` sub-package uses a clean hierarchical structure (`bd_dos.config`, `bd_dos.modelo`, etc.).
- **`frmPrincipal` is never instantiated** — commented out in `UaiLogin.main()`.
- **`frmLogin` and `frmVentas` each have their own `main()`** — usable standalone for testing UI.
- **No tests exist** — `src/test/java/` is empty, no test dependency in `pom.xml`. Add JUnit if writing tests.
- **No test/lint/format commands** are configured. Add plugins to `pom.xml` if needed.
- **Git remote**: `origin` → `https://github.com/cayoss777/javaescritorio.git`, branch `main`, 2 commits.
